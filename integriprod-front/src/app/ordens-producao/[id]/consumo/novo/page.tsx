'use client'

import { useState } from 'react'
import { useParams, useRouter } from 'next/navigation'
import { useRegistrarConsumo, useProdutos, useEstoquePorProduto } from '@/hooks/useApi'
import { Button, Input, Select, Card, BackButton } from '@/components/ui'
import toast from 'react-hot-toast'

export default function NovoConsumoPage() {
    const params = useParams()
    const router = useRouter()
    const ordemId = Number(params.id)

    const [formData, setFormData] = useState({
        produtoId: '',
        estoqueId: '',
        quantidadeConsumida: '',
        observacao: '',
        usuarioConsumo: 'admin'
    })

    const { data: produtos } = useProdutos()
    const { data: estoques, refetch: refetchEstoques } = useEstoquePorProduto(Number(formData.produtoId))
    const { mutate: registrarConsumo, isPending } = useRegistrarConsumo()

    const handleProdutoChange = (produtoId: string) => {
        setFormData({ ...formData, produtoId, estoqueId: '' })
        if (produtoId) {
            refetchEstoques()
        }
    }

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        if (!formData.produtoId || !formData.quantidadeConsumida) {
            toast.error('Preencha os campos obrigatórios')
            return
        }

        const payload: any = {
            ordemProducao: { id: ordemId },
            produto: { id: Number(formData.produtoId) },
            quantidadeConsumida: Number(formData.quantidadeConsumida),
            usuarioConsumo: formData.usuarioConsumo
        }

        if (formData.estoqueId) {
            payload.estoque = { id: Number(formData.estoqueId) }
        }
        if (formData.observacao) {
            payload.observacao = formData.observacao
        }

        registrarConsumo(payload, {
            onSuccess: () => {
                toast.success('Consumo registrado com sucesso!')
                router.push(`/ordens-producao/${ordemId}`)
            },
            onError: (error: any) => {
                toast.error(error.response?.data?.message || 'Erro ao registrar consumo')
            }
        })
    }

    const calcularDisponivel = (estoque: any) => {
        if (estoque.disponivel !== undefined) return estoque.disponivel
        return (estoque.quantidade || 0) - (estoque.reservado || 0)
    }

    const getUnidadeMedida = (estoque: any) => {
        return estoque.produto?.unidadeMedida?.sigla || 'UN'
    }

    return (
        <div className="p-6 max-w-2xl mx-auto">
            <div className="flex items-center gap-4 mb-6">
                <BackButton fallbackRoute={`/ordens-producao/${ordemId}`} />
                <h1 className="text-2xl font-bold text-white">Registrar Consumo de Material</h1>
            </div>

            <Card>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <Select
                        label="Produto *"
                        options={produtos?.map(p => ({ value: String(p.id), label: `${p.codigo} - ${p.nome}` })) || []}
                        value={formData.produtoId}
                        onChange={(e) => handleProdutoChange(e.target.value)}
                        required
                    />

                    {formData.produtoId && estoques && estoques.length > 0 && (
                        <Select
                            label="Lote (opcional)"
                            options={estoques.map(e => ({
                                value: String(e.id),
                                label: `${e.lote || 'Sem lote'} - Disponível: ${calcularDisponivel(e)} ${getUnidadeMedida(e)}`
                            }))}
                            value={formData.estoqueId}
                            onChange={(e) => setFormData({ ...formData, estoqueId: e.target.value })}
                        />
                    )}

                    <Input
                        label="Quantidade Consumida *"
                        type="number"
                        step="0.001"
                        value={formData.quantidadeConsumida}
                        onChange={(e) => setFormData({ ...formData, quantidadeConsumida: e.target.value })}
                        required
                    />

                    <Input
                        label="Observação"
                        value={formData.observacao}
                        onChange={(e) => setFormData({ ...formData, observacao: e.target.value })}
                        placeholder="Observações sobre o consumo..."
                    />

                    <div className="flex justify-end gap-3 pt-4">
                        <Button onClick={() => router.back()}>
                            Cancelar
                        </Button>
                        <Button type="submit" disabled={isPending}>
                            {isPending ? 'Registrando...' : 'Registrar Consumo'}
                        </Button>
                    </div>
                </form>
            </Card>
        </div>
    )
}