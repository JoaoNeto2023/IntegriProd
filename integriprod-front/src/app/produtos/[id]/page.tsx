'use client'

import { useState, useEffect } from 'react'
import { useRouter, useParams } from 'next/navigation'
import {
    useProduto,
    useAtualizarProduto,
    useTiposProduto,
    useUnidadesMedida,
    useFamiliasProduto
} from '@/hooks/useApi'
import { Button, Input, Select, Card, Loading } from '@/components/ui'
import toast from 'react-hot-toast'
import type { StatusProduto } from '@/types/produtos'


export default function EditarProdutoPage() {
    const router = useRouter()
    const params = useParams()
    const id = Number(params.id)

    const [formData, setFormData] = useState({
        codigo: '',
        nome: '',
        tipoProdutoId: '',
        unidadeMedidaSigla: '',
        familiaId: '',
        precoVenda: '',
        status: 'ATIVO' as StatusProduto
    })

    const { data: produto, isLoading: loadingProduto } = useProduto(id)
    const { data: tipos } = useTiposProduto()
    const { data: unidades } = useUnidadesMedida()
    const { data: familias } = useFamiliasProduto()
    const { mutate: atualizarProduto, isPending } = useAtualizarProduto(id)

    useEffect(() => {
        if (produto) {
            setFormData({
                codigo: produto.codigo,
                nome: produto.nome,
                tipoProdutoId: String(produto.tipoProduto.id),
                unidadeMedidaSigla: produto.unidadeMedida.sigla,
                familiaId: produto.familiaProduto?.id ? String(produto.familiaProduto.id) : '',
                precoVenda: produto.precoVenda?.toString() || '',
                status: produto.status
            })
        }
    }, [produto])

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        e.stopPropagation()

        console.log('Submit edição disparado')
        console.log('FormData:', formData)

        if (!formData.codigo || !formData.nome || !formData.tipoProdutoId || !formData.unidadeMedidaSigla) {
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

        console.log('Payload edição:', payload)

        atualizarProduto(payload, {
            onSuccess: (data) => {
                console.log('Produto atualizado:', data)
                toast.success('Produto atualizado com sucesso!')
                router.push('/produtos')
            },
            onError: (error: any) => {
                console.error('Erro ao atualizar:', error)
                toast.error(error.response?.data?.message || 'Erro ao atualizar produto')
            }
        })
    }

    if (loadingProduto) return <Loading fullScreen />

    return (
        <div className="p-6 max-w-2xl mx-auto">
            // No início do return, antes do Card:
            <BackButton fallbackRoute="/produtos" label="Voltar para Produtos" />
            <Card title="Editar Produto">
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

                    <Select
                        label="Status"
                        options={[
                            { value: 'ATIVO', label: 'Ativo' },
                            { value: 'INATIVO', label: 'Inativo' }
                        ]}
                        value={formData.status}
                        onChange={(e) => setFormData({
                            ...formData,
                            status: e.target.value as StatusProduto
                        })}
                        className="text-gray-900"
                    />

                    <div className="flex justify-end gap-3 pt-4">

                        <Button
                            variant="secondary"
                            onClick={() => router.push(`/produtos/${id}/ficha-tecnica`)}
                        >
                            Ver Ficha Técnica
                        </Button>
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