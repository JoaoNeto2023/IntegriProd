'use client'

import { useState } from 'react'
import { useProdutos, useExcluirProduto } from '@/hooks/useApi'
import { Button, Input, Table, Badge, Loading, BackButton } from '@/components/ui'
import Link from 'next/link'
import toast from 'react-hot-toast'

export default function ProdutosPage() {
    const [search, setSearch] = useState('')
    const [searchTerm, setSearchTerm] = useState('')
    const [mostrarInativos, setMostrarInativos] = useState(false)
    const [itensOcultados, setItensOcultados] = useState<number[]>([])

    const { data: produtos, isLoading, refetch } = useProdutos()
    const { mutate: excluirProduto } = useExcluirProduto()

    const produtosAtivos = produtos?.filter(p =>
        p.status === 'ATIVO' && !itensOcultados.includes(p.id)
    ) || []

    const produtosInativos = produtos?.filter(p =>
        p.status === 'INATIVO' && !itensOcultados.includes(p.id)
    ) || []

    const produtosBase = mostrarInativos ? produtosInativos : produtosAtivos

    const handleSearch = () => {
        setSearchTerm(search)
        if (!search) refetch()
    }

    const produtosFiltrados = searchTerm
        ? produtosBase.filter(p =>
            p.nome.toLowerCase().includes(searchTerm.toLowerCase()) ||
            p.codigo.toLowerCase().includes(searchTerm.toLowerCase())
        )
        : produtosBase

    const handleExcluir = (id: number, nome: string) => {
        if (confirm(`Deseja realmente inativar o produto "${nome}"?`)) {
            excluirProduto(id, {
                onSuccess: () => {
                    toast.success('Produto inativado com sucesso!')
                    refetch()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao inativar produto')
                }
            })
        }
    }

    const handleOcultar = (id: number, nome: string) => {
        if (confirm(`Deseja ocultar o produto "${nome}" da lista de inativos?`)) {
            setItensOcultados([...itensOcultados, id])
            toast.success('Produto ocultado da visualização!')
        }
    }

    const handleLimparOcultados = () => {
        if (confirm('Deseja mostrar novamente todos os produtos ocultados?')) {
            setItensOcultados([])
            toast.success('Produtos ocultados agora estão visíveis!')
        }
    }

    if (isLoading) return <Loading fullScreen />

    return (
        <div className="p-6">
            <div className="flex items-center gap-4 mb-6">
                <BackButton fallbackRoute="/" />
                <h1 className="text-2xl font-bold text-white">
                    {mostrarInativos ? 'Produtos Inativos' : 'Produtos Ativos'}
                </h1>
            </div>

            <div className="flex justify-between items-center mb-6">
                <div className="flex gap-2">
                    <Button
                        variant={!mostrarInativos ? 'primary' : 'secondary'}
                        onClick={() => {
                            setMostrarInativos(false)
                            setSearch('')
                            setSearchTerm('')
                        }}
                        size="sm"
                    >
                        Ativos ({produtosAtivos.length})
                    </Button>
                    <Button
                        variant={mostrarInativos ? 'primary' : 'secondary'}
                        onClick={() => {
                            setMostrarInativos(true)
                            setSearch('')
                            setSearchTerm('')
                        }}
                        size="sm"
                    >
                        Inativos ({produtosInativos.length})
                    </Button>
                </div>
                <div className="flex gap-2">
                    {itensOcultados.length > 0 && (
                        <Button onClick={handleLimparOcultados} variant="secondary" size="sm">
                            Mostrar Ocultados ({itensOcultados.length})
                        </Button>
                    )}
                    <Button href="/produtos/novo">Novo Produto</Button>
                </div>
            </div>

            <div className="flex gap-4 mb-6">
                <Input
                    placeholder={`Buscar por nome ou código...`}
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
                    className="text-gray-900"
                />
                <Button onClick={handleSearch} variant="secondary">
                    Buscar
                </Button>
                {searchTerm && (
                    <Button
                        variant="ghost"
                        onClick={() => {
                            setSearch('')
                            setSearchTerm('')
                        }}
                    >
                        Limpar
                    </Button>
                )}
            </div>

            {produtosFiltrados.length === 0 ? (
                <div className="text-center py-12 text-gray-400">
                    Nenhum {mostrarInativos ? 'produto inativo' : 'produto ativo'} encontrado.
                </div>
            ) : (
                <Table>
                    <thead>
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase">Código</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase">Nome</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase">Tipo</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase">Unidade</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase">Preço</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase">Status</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-300 uppercase">Ações</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {produtosFiltrados.map((produto) => (
                            <tr key={produto.id} className="hover:bg-gray-50">
                                <td className="px-6 py-4 whitespace-nowrap font-mono text-sm text-gray-900">{produto.codigo}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{produto.nome}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{produto.tipoProduto.descricao}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{produto.unidadeMedida.sigla}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                                    R$ {produto.precoVenda?.toFixed(2) ?? '0,00'}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm">
                                    <Badge variant={produto.status === 'ATIVO' ? 'success' : 'danger'}>
                                        {produto.status}
                                    </Badge>
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm space-x-2">
                                    <Button href={`/produtos/${produto.id}`} size="sm">
                                        Editar
                                    </Button>
                                    {mostrarInativos ? (
                                        <Button
                                            size="sm"
                                            variant="secondary"
                                            onClick={() => handleOcultar(produto.id, produto.nome)}
                                        >
                                            Ocultar
                                        </Button>
                                    ) : (
                                        <Button
                                            size="sm"
                                            variant="danger"
                                            onClick={() => handleExcluir(produto.id, produto.nome)}
                                        >
                                            Inativar
                                        </Button>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            )}
        </div>
    )
}