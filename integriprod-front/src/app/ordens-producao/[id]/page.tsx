'use client'

import { useState } from 'react'
import { useParams, useRouter } from 'next/navigation'
import {
    useOrdem,
    useIniciarOrdem,
    usePausarOrdem,
    useConcluirOrdem,
    useCancelarOrdem,
    useApontamentosPorOP,
    useConsumosPorOP
} from '@/hooks/useApi'
import { Button, Card, Loading, Badge, BackButton, Tabs } from '@/components/ui'
import toast from 'react-hot-toast'

export default function DetalheOrdemPage() {
    const params = useParams()
    const router = useRouter()
    const id = Number(params.id)
    const [activeTab, setActiveTab] = useState('dados')

    const { data: ordem, isLoading } = useOrdem(id)
    const { data: apontamentos } = useApontamentosPorOP(id)
    const { data: consumos } = useConsumosPorOP(id)
    const { mutate: iniciarOrdem } = useIniciarOrdem()
    const { mutate: pausarOrdem } = usePausarOrdem()
    const { mutate: concluirOrdem } = useConcluirOrdem()
    const { mutate: cancelarOrdem } = useCancelarOrdem()

    const getStatusBadge = (status: string) => {
        const classes: Record<string, string> = {
            PLANEJADA: 'bg-blue-100 text-blue-800',
            LIBERADA: 'bg-yellow-100 text-yellow-800',
            EM_PRODUCAO: 'bg-yellow-100 text-yellow-800',
            PAUSADA: 'bg-gray-100 text-gray-800',
            CONCLUIDA: 'bg-green-100 text-green-800',
            CANCELADA: 'bg-red-100 text-red-800'
        }
        return classes[status] || 'bg-gray-100 text-gray-800'
    }

    const handleIniciar = () => {
        if (confirm(`Iniciar a produção da OP ${ordem?.numeroOp}?`)) {
            iniciarOrdem(id, {
                onSuccess: () => {
                    toast.success('Ordem iniciada com sucesso!')
                    router.refresh()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao iniciar ordem')
                }
            })
        }
    }

    const handlePausar = () => {
        if (confirm(`Pausar a produção da OP ${ordem?.numeroOp}?`)) {
            pausarOrdem(id, {
                onSuccess: () => {
                    toast.success('Ordem pausada com sucesso!')
                    router.refresh()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao pausar ordem')
                }
            })
        }
    }

    const handleConcluir = () => {
        if (confirm(`Concluir a OP ${ordem?.numeroOp}?`)) {
            concluirOrdem(id, {
                onSuccess: () => {
                    toast.success('Ordem concluída com sucesso!')
                    router.refresh()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao concluir ordem')
                }
            })
        }
    }

    const handleCancelar = () => {
        if (confirm(`Cancelar a OP ${ordem?.numeroOp}?`)) {
            cancelarOrdem(id, {
                onSuccess: () => {
                    toast.success('Ordem cancelada com sucesso!')
                    router.refresh()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao cancelar ordem')
                }
            })
        }
    }

    if (isLoading) return <Loading fullScreen />
    if (!ordem) return <div>Ordem não encontrada</div>

    const tabs = [
        {
            id: 'dados',
            label: 'Dados da Ordem',
            content: (
                <div className="space-y-6">
                    <div className="grid grid-cols-2 gap-4">
                        <div>
                            <p className="text-sm text-gray-500">Número da OP</p>
                            <p className="text-lg font-medium text-gray-900">{ordem.numeroOp}</p>
                        </div>
                        <div>
                            <p className="text-sm text-gray-500">Status</p>
                            <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusBadge(ordem.status)}`}>
                                {ordem.status.replace('_', ' ')}
                            </span>
                        </div>
                        <div>
                            <p className="text-sm text-gray-500">Filial</p>
                            <p className="text-gray-900">{ordem.filial?.nome || '-'}</p>
                        </div>
                        <div>
                            <p className="text-sm text-gray-500">Produto</p>
                            <p className="text-gray-900">{ordem.produto?.nome || '-'}</p>
                            <p className="text-sm text-gray-500">Cód: {ordem.produto?.codigo || '-'}</p>
                        </div>
                        <div>
                            <p className="text-sm text-gray-500">Quantidade Planejada</p>
                            <p className="text-lg font-medium text-gray-900">{ordem.quantidadePlanejada}</p>
                        </div>
                        <div>
                            <p className="text-sm text-gray-500">Quantidade Produzida</p>
                            <p className="text-lg font-medium text-gray-900">{ordem.quantidadeProduzida || 0}</p>
                        </div>
                        <div>
                            <p className="text-sm text-gray-500">Data Emissão</p>
                            <p className="text-gray-900">{new Date(ordem.dataEmissao).toLocaleDateString('pt-BR')}</p>
                        </div>
                        <div>
                            <p className="text-sm text-gray-500">Previsão</p>
                            <p className="text-gray-900">
                                {ordem.dataInicioPrevista ? new Date(ordem.dataInicioPrevista).toLocaleDateString('pt-BR') : '-'} até{' '}
                                {ordem.dataFimPrevista ? new Date(ordem.dataFimPrevista).toLocaleDateString('pt-BR') : '-'}
                            </p>
                        </div>
                        <div>
                            <p className="text-sm text-gray-500">Prioridade</p>
                            <p className="text-gray-900">{ordem.prioridade}</p>
                        </div>
                    </div>

                    {ordem.observacoes && (
                        <div>
                            <p className="text-sm text-gray-500">Observações</p>
                            <p className="text-gray-900 mt-1">{ordem.observacoes}</p>
                        </div>
                    )}

                    <div className="border-t pt-4">
                        <h3 className="text-lg font-medium text-gray-900 mb-3">Ações</h3>
                        <div className="flex gap-2">
                            {ordem.status === 'PLANEJADA' && (
                                <Button onClick={handleIniciar}>Iniciar Produção</Button>
                            )}
                            {ordem.status === 'EM_PRODUCAO' && (
                                <>
                                    <Button onClick={handlePausar}>Pausar</Button>
                                    <Button onClick={handleConcluir}>Concluir</Button>
                                </>
                            )}
                            {ordem.status === 'PAUSADA' && (
                                <Button onClick={handleIniciar}>Retomar</Button>
                            )}
                            {(ordem.status === 'PLANEJADA' || ordem.status === 'PAUSADA') && (
                                <Button onClick={handleCancelar} variant="danger">Cancelar</Button>
                            )}
                        </div>
                    </div>
                </div>
            )
        },
        {
            id: 'apontamentos',
            label: 'Apontamentos',
            badge: apontamentos?.length,
            content: (
                <div className="space-y-4">
                    <div className="flex justify-end">
                        <Button href={`/ordens-producao/${id}/apontamentos/novo`} size="sm">
                            Novo Apontamento
                        </Button>
                    </div>
                    {!apontamentos?.length ? (
                        <p className="text-center py-8 text-gray-500">
                            Nenhum apontamento registrado.
                        </p>
                    ) : (
                        <div className="space-y-3">
                            {apontamentos.map((ap) => (
                                <div key={ap.id} className="border rounded-lg p-4">
                                    <div className="flex justify-between mb-2">
                                        <span className="text-sm font-medium text-gray-700">
                                            {new Date(ap.dataApontamento).toLocaleDateString('pt-BR')}
                                        </span>
                                        <Badge variant="info">{ap.tipoApontamento}</Badge>
                                    </div>
                                    <p className="text-gray-900">Quantidade: {ap.quantidadeProduzida}</p>
                                    {ap.postoTrabalho && (
                                        <p className="text-sm text-gray-600">Posto: {ap.postoTrabalho.nome}</p>
                                    )}
                                    {ap.observacoes && (
                                        <p className="text-sm text-gray-500 mt-2">{ap.observacoes}</p>
                                    )}
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            )
        },
        {
            id: 'consumo',
            label: 'Consumo de Materiais',
            badge: consumos?.length,
            content: (
                <div className="space-y-4">
                    <div className="flex justify-end">
                        <Button href={`/ordens-producao/${id}/consumo/novo`} size="sm">
                            Registrar Consumo
                        </Button>
                    </div>
                    {!consumos?.length ? (
                        <p className="text-center py-8 text-gray-500">
                            Nenhum consumo registrado.
                        </p>
                    ) : (
                        <div className="space-y-3">
                            {consumos.map((consumo) => (
                                <div key={consumo.id} className="border rounded-lg p-4">
                                    <div className="flex justify-between mb-2">
                                        <span className="text-sm font-medium text-gray-700">
                                            {consumo.produto?.nome || 'Produto'}
                                        </span>
                                        <span className="text-sm text-gray-500">
                                            {new Date(consumo.dataConsumo).toLocaleDateString('pt-BR')}
                                        </span>
                                    </div>
                                    <p className="text-gray-900">Quantidade: {consumo.quantidadeConsumida}</p>
                                    {consumo.estoque?.lote && (
                                        <p className="text-sm text-gray-600">Lote: {consumo.estoque.lote}</p>
                                    )}
                                    {consumo.observacao && (
                                        <p className="text-sm text-gray-500 mt-2">{consumo.observacao}</p>
                                    )}
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            )
        }
    ]

    return (
        <div className="p-6">
            <div className="flex items-center gap-4 mb-6">
                <BackButton fallbackRoute="/ordens-producao" />
                <h1 className="text-2xl font-bold text-white">
                    Ordem de Produção: {ordem.numeroOp}
                </h1>
            </div>

            <Card>
                <Tabs tabs={tabs} defaultTab="dados" onChange={setActiveTab} />
            </Card>
        </div>
    )
}