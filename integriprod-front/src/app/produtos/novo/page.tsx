'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useCriarProduto, useTiposProduto, useUnidadesMedida, useFamiliasProduto } from '@/hooks/useApi'
import { Button, Input, Select, Card, Loading } from '@/components/ui'
import toast from 'react-hot-toast'
import type { StatusProduto } from '@/types/produtos'

export default function NovoProdutoPage() {
    const router = useRouter()
    const [formData, setFormData] = useState({
        codigo: '',
        nome: '',
        tipoProdutoId: '',
        unidadeMedidaSigla: '',
        familiaId: '',
        precoVenda: '',
        status: 'ATIVO' as StatusProduto
    })

    const { data: tipos, isLoading: loadingTipos } = useTiposProduto()
    const { data: unidades, isLoading: loadingUnidades } = useUnidadesMedida()
    const { data: familias } = useFamiliasProduto()
    const { mutate: criarProduto, isPending } = useCriarProduto()

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        e.stopPropagation()

        console.log('Submit disparado')
        console.log('FormData:', formData)

        if (!formData.codigo || !formData.nome || !formData.tipoProdutoId || !formData.unidadeMedidaSigla) {
            console.log('Campos obrigatórios faltando')
            toast.error('Preencha os campos obrigatórios')
            return
        }

        const payload = {
            codigo: formData.codigo,
            nome: formData.nome,
            tipoProduto: { id: Number(formData.tipoProdutoId) },
            unidadeMedida: { sigla: formData.unidadeMedidaSigla },
            familiaProduto: formData.familiaId ? { id: Number(formData.familiaId) } : undefined,
            precoVenda: formData.precoVenda ? Number(formData.precoVenda) : undefined,
            status: formData.status
        }

        console.log('Payload:', payload)

        criarProduto(payload, {
            onSuccess: (data) => {
                console.log('Sucesso:', data)
                toast.success('Produto criado com sucesso!')
                router.push('/produtos')
            },
            onError: (error: any) => {
                console.error('Erro:', error)
                toast.error(error.response?.data?.message || 'Erro ao criar produto')
            }
        })
    }

    if (loadingTipos || loadingUnidades) return <Loading fullScreen />

    return (
        <div className="p-6 max-w-2xl mx-auto">
            <Card title="Novo Produto">
                <form onSubmit={handleSubmit} className="space-y-4" noValidate>
                    <Input
                        label="Código *"
                        value={formData.codigo}
                        onChange={(e) => setFormData({ ...formData, codigo: e.target.value })}
                        required
                        className="text-gray-900"
                    />

                    <Input
                        label="Nome *"
                        value={formData.nome}
                        onChange={(e) => setFormData({ ...formData, nome: e.target.value })}
                        required
                        className="text-gray-900"
                    />

                    <Select
                        label="Tipo de Produto *"
                        options={tipos?.map(t => ({ value: String(t.id), label: t.descricao })) || []}
                        value={formData.tipoProdutoId}
                        onChange={(e) => setFormData({
                            ...formData,
                            tipoProdutoId: e.target.value
                        })}
                        required
                        className="text-gray-900"
                    />

                    <Select
                        label="Unidade de Medida *"
                        options={unidades?.map(u => ({ value: u.sigla, label: `${u.sigla} - ${u.descricao}` })) || []}
                        value={formData.unidadeMedidaSigla}
                        onChange={(e) => setFormData({
                            ...formData,
                            unidadeMedidaSigla: e.target.value
                        })}
                        required
                        className="text-gray-900"
                    />

                    <Select
                        label="Família"
                        options={familias?.map(f => ({ value: String(f.id), label: f.nome })) || []}
                        value={formData.familiaId}
                        onChange={(e) => setFormData({
                            ...formData,
                            familiaId: e.target.value
                        })}
                        className="text-gray-900"
                    />

                    <Input
                        label="Preço de Venda (R$)"
                        type="number"
                        step="0.01"
                        value={formData.precoVenda}
                        onChange={(e) => setFormData({ ...formData, precoVenda: e.target.value })}
                        className="text-gray-900"
                    />

                    <div className="flex justify-end gap-3 pt-4">
                        <Button
                            variant="ghost"
                            onClick={() => router.push('/produtos')}
                        >
                            Cancelar
                        </Button>
                        <Button
                            type="submit"
                            disabled={isPending}
                        >
                            {isPending ? 'Salvando...' : 'Salvar'}
                        </Button>
                    </div>
                </form>
            </Card>
        </div>
    )
}