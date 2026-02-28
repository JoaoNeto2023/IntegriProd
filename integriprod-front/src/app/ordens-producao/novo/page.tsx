'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useCriarOrdem, useProdutos, useFiliais, useFuncionarios } from '@/hooks/useApi'
import { Button, Input, Select, Card, Loading, BackButton } from '@/components/ui'
import toast from 'react-hot-toast'

export default function NovaOrdemPage() {
    const router = useRouter()
    const [formData, setFormData] = useState({
        filialId: '',
        numeroOp: '',
        produtoId: '',
        versaoEstrutura: '',
        quantidadePlanejada: '',
        dataEmissao: new Date().toISOString().split('T')[0],
        dataInicioPrevista: '',
        dataFimPrevista: '',
        prioridade: '5',
        responsavelId: '',
        observacoes: '',
        usuarioCriacao: 'admin'
    })

    const { data: produtos, isLoading: loadingProdutos } = useProdutos()
    const { data: filiais, isLoading: loadingFiliais } = useFiliais()
    const { data: funcionarios, isLoading: loadingFuncionarios } = useFuncionarios()
    const { mutate: criarOrdem, isPending } = useCriarOrdem()

    const isLoading = loadingProdutos || loadingFiliais || loadingFuncionarios

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        e.stopPropagation()

        if (!formData.filialId || !formData.numeroOp || !formData.produtoId || !formData.quantidadePlanejada) {
            toast.error('Preencha os campos obrigatórios')
            return
        }

        // Encontra os objetos completos selecionados
        const filialSelecionada = filiais?.find(f => f.id === Number(formData.filialId))
        const produtoSelecionado = produtos?.find(p => p.id === Number(formData.produtoId))
        const responsavelSelecionado = funcionarios?.find(f => f.id === Number(formData.responsavelId))

        if (!filialSelecionada || !produtoSelecionado) {
            toast.error('Erro ao carregar dados selecionados')
            return
        }

        const payload = {
            filial: filialSelecionada,
            numeroOp: formData.numeroOp,
            produto: produtoSelecionado,
            versaoEstrutura: formData.versaoEstrutura || undefined,
            quantidadePlanejada: Number(formData.quantidadePlanejada),
            dataEmissao: formData.dataEmissao,
            dataInicioPrevista: formData.dataInicioPrevista || undefined,
            dataFimPrevista: formData.dataFimPrevista || undefined,
            prioridade: Number(formData.prioridade),
            responsavel: responsavelSelecionado,
            observacoes: formData.observacoes || undefined,
            usuarioCriacao: formData.usuarioCriacao,
            status: 'PLANEJADA' as const
        }

        criarOrdem(payload, {
            onSuccess: () => {
                toast.success('Ordem de produção criada com sucesso!')
                router.push('/ordens-producao')
            },
            onError: (error: any) => {
                toast.error(error.response?.data?.message || 'Erro ao criar ordem')
            }
        })
    }

    if (isLoading) return <Loading fullScreen />

    return (
        <div className="p-6 max-w-2xl mx-auto">
            <div className="flex items-center gap-4 mb-6">
                <BackButton fallbackRoute="/ordens-producao" label="Voltar para Ordens" />
                <h1 className="text-2xl font-bold text-white">Nova Ordem de Produção</h1>
            </div>

            <Card>
                <form onSubmit={handleSubmit} className="space-y-4" noValidate>
                    <div className="grid grid-cols-2 gap-4">
                        <Select
                            label="Filial *"
                            options={filiais?.map(f => ({ value: String(f.id), label: f.nome })) || []}
                            value={formData.filialId}
                            onChange={(e) => setFormData({ ...formData, filialId: e.target.value })}
                            required
                        />

                        <Input
                            label="Número da OP *"
                            value={formData.numeroOp}
                            onChange={(e) => setFormData({ ...formData, numeroOp: e.target.value })}
                            placeholder="OP-001"
                            required
                        />
                    </div>

                    <Select
                        label="Produto *"
                        options={produtos?.map(p => ({ value: String(p.id), label: `${p.codigo} - ${p.nome}` })) || []}
                        value={formData.produtoId}
                        onChange={(e) => setFormData({ ...formData, produtoId: e.target.value })}
                        required
                    />

                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="Versão da Estrutura"
                            value={formData.versaoEstrutura}
                            onChange={(e) => setFormData({ ...formData, versaoEstrutura: e.target.value })}
                            placeholder="1.0"
                        />

                        <Input
                            label="Quantidade Planejada *"
                            type="number"
                            step="0.01"
                            value={formData.quantidadePlanejada}
                            onChange={(e) => setFormData({ ...formData, quantidadePlanejada: e.target.value })}
                            required
                        />
                    </div>

                    <div className="grid grid-cols-3 gap-4">
                        <Input
                            label="Data Emissão"
                            type="date"
                            value={formData.dataEmissao}
                            onChange={(e) => setFormData({ ...formData, dataEmissao: e.target.value })}
                        />

                        <Input
                            label="Início Previsto"
                            type="date"
                            value={formData.dataInicioPrevista}
                            onChange={(e) => setFormData({ ...formData, dataInicioPrevista: e.target.value })}
                        />

                        <Input
                            label="Fim Previsto"
                            type="date"
                            value={formData.dataFimPrevista}
                            onChange={(e) => setFormData({ ...formData, dataFimPrevista: e.target.value })}
                        />
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        <Select
                            label="Prioridade"
                            options={[
                                { value: '1', label: '1 - Muito Baixa' },
                                { value: '2', label: '2 - Baixa' },
                                { value: '3', label: '3 - Média' },
                                { value: '4', label: '4 - Alta' },
                                { value: '5', label: '5 - Muito Alta' }
                            ]}
                            value={formData.prioridade}
                            onChange={(e) => setFormData({ ...formData, prioridade: e.target.value })}
                        />

                        <Select
                            label="Responsável"
                            options={funcionarios?.map(f => ({ value: String(f.id), label: f.nomeRazao })) || []}
                            value={formData.responsavelId}
                            onChange={(e) => setFormData({ ...formData, responsavelId: e.target.value })}
                        />
                    </div>

                    <Input
                        label="Observações"
                        value={formData.observacoes}
                        onChange={(e) => setFormData({ ...formData, observacoes: e.target.value })}
                        placeholder="Observações sobre a ordem..."
                    />

                    <div className="flex justify-end gap-3 pt-4">
                        <Button
                            onClick={() => router.push('/ordens-producao')}
                        >
                            Cancelar
                        </Button>
                        <Button
                            type="submit"
                            disabled={isPending}
                        >
                            {isPending ? 'Salvando...' : 'Criar Ordem'}
                        </Button>
                    </div>
                </form>
            </Card>
        </div>
    )
}