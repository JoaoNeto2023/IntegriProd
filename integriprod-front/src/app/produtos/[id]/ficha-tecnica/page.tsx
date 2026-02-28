'use client'

import { useState } from 'react'
import { useParams, useRouter } from 'next/navigation'
import {
    useProduto,
    useFichaTecnica,
    useVersoesFichaTecnica,
    useCriarItemFicha,
    useExcluirItemFicha,
    useCalcularCustoFicha,
    useProdutos,
    useUnidadesMedida
} from '@/hooks/useApi'
import { Card, Button, Select, Input, Table, Badge, Loading, Modal } from '@/components/ui'
import toast from 'react-hot-toast'
import type { FichaTecnicaPayload } from '@/types/produtos'

export default function FichaTecnicaPage() {
    const params = useParams()
    const router = useRouter()
    const produtoId = Number(params.id)
    const [versao, setVersao] = useState('1.0')
    const [showModal, setShowModal] = useState(false)
    const [novoItem, setNovoItem] = useState({
        produtoFilhoId: '',
        quantidade: '',
        unidadeMedidaSigla: '',
        percPerda: '0',
        ordemProducao: '1'
    })

    const { data: produto } = useProduto(produtoId)
    const { data: ficha, isLoading: loadingFicha } = useFichaTecnica(produtoId, versao)
    const { data: versoes } = useVersoesFichaTecnica(produtoId)
    const { data: custo } = useCalcularCustoFicha(produtoId, versao)
    const { data: produtos } = useProdutos()
    const { data: unidades } = useUnidadesMedida()
    const { mutate: criarItem, isPending: criando } = useCriarItemFicha()
    const { mutate: excluirItem } = useExcluirItemFicha(produtoId)

    const handleAddItem = () => {
        if (!novoItem.produtoFilhoId || !novoItem.quantidade || !novoItem.unidadeMedidaSigla) {
            toast.error('Preencha todos os campos obrigatórios')
            return
        }

        const payload: FichaTecnicaPayload = {
            produtoPai: { id: produtoId },
            produtoFilho: { id: Number(novoItem.produtoFilhoId) },
            versao,
            quantidade: Number(novoItem.quantidade),
            unidadeMedida: { sigla: novoItem.unidadeMedidaSigla },
            nivel: 1,
            tipoComponente: 'MP',
            percPerda: Number(novoItem.percPerda),
            ordemProducao: Number(novoItem.ordemProducao),
            dataInicioVigencia: new Date().toISOString().split('T')[0]
        }

        criarItem(payload, {
            onSuccess: () => {
                setShowModal(false)
                setNovoItem({
                    produtoFilhoId: '',
                    quantidade: '',
                    unidadeMedidaSigla: '',
                    percPerda: '0',
                    ordemProducao: '1'
                })
            }
        })
    }

    const handleExcluir = (id: number) => {
        if (confirm('Remover este item da ficha técnica?')) {
            excluirItem(id)
        }
    }

    if (loadingFicha) return <Loading fullScreen />

    return (
        <div className="p-6">
            <div className="flex justify-between items-center mb-6">
                <div>
                    <h1 className="text-2xl font-bold text-white">Ficha Técnica</h1>
                    <p className="text-gray-200">{produto?.nome} - {produto?.codigo}</p>
                </div>
                <div className="flex gap-3">
                    <Button variant="ghost" onClick={() => router.back()}>
                        Voltar
                    </Button>
                    <Button onClick={() => setShowModal(true)}>
                        Adicionar Item
                    </Button>
                </div>
            </div>

            <div className="grid grid-cols-1 lg:grid-cols-4 gap-6 mb-6">
                <Card className="lg:col-span-3">
                    <div className="flex gap-4 items-center mb-4">
                        <Select
                            label="Versão"
                            options={versoes?.map(v => ({ value: v, label: `Versão ${v}` })) || []}
                            value={versao}
                            onChange={(e) => setVersao(e.target.value)}
                            className="w-48"
                        />
                        <span className="text-sm text-gray-500">
                            {ficha?.length || 0} itens
                        </span>
                    </div>

                    <Table>
                        <thead>
                            <tr>
                                <th className="text-left">Ordem</th>
                                <th className="text-left">Componente</th>
                                <th className="text-left">Quantidade</th>
                                <th className="text-left">Unidade</th>
                                <th className="text-left">Perda (%)</th>
                                <th className="text-left">Tipo</th>
                                <th className="text-left">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            {ficha?.map((item) => (
                                <tr key={item.id}>
                                    <td>{item.ordemProducao}</td>
                                    <td>
                                        <div>
                                            <p className="font-medium text-gray-900">{item.produtoFilho.nome}</p>
                                            <p className="text-sm text-gray-500">{item.produtoFilho.codigo}</p>
                                        </div>
                                    </td>
                                    <td className="text-gray-900">{item.quantidade}</td>
                                    <td className="text-gray-900">{item.unidadeMedida.sigla}</td>
                                    <td className="text-gray-900">{item.percPerda}%</td>
                                    <td>
                                        <Badge variant="info">{item.tipoComponente}</Badge>
                                    </td>
                                    <td>
                                        <Button
                                            size="sm"
                                            variant="danger"
                                            onClick={() => handleExcluir(item.id)}
                                        >
                                            Remover
                                        </Button>
                                    </td>
                                </tr>
                            ))}
                            {(!ficha || ficha.length === 0) && (
                                <tr>
                                    <td colSpan={7} className="text-center py-8 text-gray-500">
                                        Nenhum item na ficha técnica desta versão
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </Table>
                </Card>

                <Card title="Resumo">
                    <div className="space-y-4">
                        <div>
                            <p className="text-sm text-gray-600">Total de Itens</p>
                            <p className="text-2xl font-bold text-gray-900">{ficha?.length || 0}</p>
                        </div>
                        <div>
                            <p className="text-sm text-gray-600">Custo Total Estimado</p>
                            <p className="text-2xl font-bold text-blue-600">
                                R$ {custo?.custo?.toFixed(2) || '0,00'}
                            </p>
                        </div>
                        <div className="pt-4 border-t">
                            <Button variant="secondary" className="w-full">
                                Nova Versão
                            </Button>
                        </div>
                    </div>
                </Card>
            </div>

            <Modal
                isOpen={showModal}
                onClose={() => setShowModal(false)}
                onConfirm={handleAddItem}
                title="Adicionar Componente"
                confirmText="Adicionar"
            >
                <div className="space-y-4">
                    <Select
                        label="Componente *"
                        options={produtos
                            ?.filter(p => p.id !== produtoId)
                            .map(p => ({ value: String(p.id), label: `${p.codigo} - ${p.nome}` })) || []}
                        value={novoItem.produtoFilhoId}
                        onChange={(e) => setNovoItem({ ...novoItem, produtoFilhoId: e.target.value })}
                        required
                    />

                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="Quantidade *"
                            type="number"
                            step="0.001"
                            value={novoItem.quantidade}
                            onChange={(e) => setNovoItem({ ...novoItem, quantidade: e.target.value })}
                            required
                        />
                        <Select
                            label="Unidade *"
                            options={unidades?.map(u => ({ value: u.sigla, label: `${u.sigla} - ${u.descricao}` })) || []}
                            value={novoItem.unidadeMedidaSigla}
                            onChange={(e) => setNovoItem({ ...novoItem, unidadeMedidaSigla: e.target.value })}
                            required
                        />
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="% Perda"
                            type="number"
                            step="0.1"
                            value={novoItem.percPerda}
                            onChange={(e) => setNovoItem({ ...novoItem, percPerda: e.target.value })}
                        />
                        <Input
                            label="Ordem"
                            type="number"
                            value={novoItem.ordemProducao}
                            onChange={(e) => setNovoItem({ ...novoItem, ordemProducao: e.target.value })}
                        />
                    </div>
                </div>
            </Modal>
        </div>
    )
}