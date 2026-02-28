'use client'

import { useState } from 'react'
import { useOrdens, useIniciarOrdem, usePausarOrdem, useConcluirOrdem, useCancelarOrdem } from '@/hooks/useApi'
import { Button, Input, Table, Badge, Loading, Card, BackButton } from '@/components/ui'
import Link from 'next/link'
import toast from 'react-hot-toast'

export default function OrdensProducaoPage() {
    const [search, setSearch] = useState('')
    const [searchTerm, setSearchTerm] = useState('')
    const [filtroStatus, setFiltroStatus] = useState<string>('TODAS')

    const { data: ordens, isLoading, refetch } = useOrdens()
    const { mutate: iniciarOrdem } = useIniciarOrdem()
    const { mutate: pausarOrdem } = usePausarOrdem()
    const { mutate: concluirOrdem } = useConcluirOrdem()
    const { mutate: cancelarOrdem } = useCancelarOrdem()

    const statusOptions = [
        { value: 'TODAS', label: 'Todos os Status' },
        { value: 'PLANEJADA', label: 'Planejadas' },
        { value: 'LIBERADA', label: 'Liberadas' },
        { value: 'EM_PRODUCAO', label: 'Em Produção' },
        { value: 'PAUSADA', label: 'Pausadas' },
        { value: 'CONCLUIDA', label: 'Concluídas' },
        { value: 'CANCELADA', label: 'Canceladas' }
    ]

    const handleSearch = () => {
        setSearchTerm(search)
        if (!search) refetch()
    }

    const ordensFiltradas = ordens?.filter(op => {
        const matchSearch = searchTerm
            ? op.numeroOp.toLowerCase().includes(searchTerm.toLowerCase()) ||
            op.produto.nome.toLowerCase().includes(searchTerm.toLowerCase())
            : true

        const matchStatus = filtroStatus === 'TODAS' ? true : op.status === filtroStatus

        return matchSearch && matchStatus
    })

    const getStatusClasses = (status: string) => {
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

    const handleIniciar = (id: number, numeroOp: string) => {
        if (confirm(`Iniciar a produção da OP ${numeroOp}?`)) {
            iniciarOrdem(id, {
                onSuccess: () => {
                    toast.success('Ordem iniciada com sucesso!')
                    refetch()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao iniciar ordem')
                }
            })
        }
    }

    const handlePausar = (id: number, numeroOp: string) => {
        if (confirm(`Pausar a produção da OP ${numeroOp}?`)) {
            pausarOrdem(id, {
                onSuccess: () => {
                    toast.success('Ordem pausada com sucesso!')
                    refetch()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao pausar ordem')
                }
            })
        }
    }

    const handleConcluir = (id: number, numeroOp: string) => {
        if (confirm(`Concluir a OP ${numeroOp}?`)) {
            concluirOrdem(id, {
                onSuccess: () => {
                    toast.success('Ordem concluída com sucesso!')
                    refetch()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao concluir ordem')
                }
            })
        }
    }

    const handleCancelar = (id: number, numeroOp: string) => {
        if (confirm(`Cancelar a OP ${numeroOp}?`)) {
            cancelarOrdem(id, {
                onSuccess: () => {
                    toast.success('Ordem cancelada com sucesso!')
                    refetch()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao cancelar ordem')
                }
            })
        }
    }

    if (isLoading) return <Loading fullScreen />

    return (
        <div className="p-6">
            <div className="flex items-center gap-4 mb-6">
                <BackButton fallbackRoute="/" />
                <h1 className="text-2xl font-bold text-white">Ordens de Produção</h1>
            </div>

            <div className="flex justify-between items-center mb-6">
                <div className="flex gap-4 flex-1">
                    <Input
                        placeholder="Buscar por número da OP ou produto..."
                        value={search}
                        onChange={(e) => setSearch(e.target.value)}
                        onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
                        className="text-gray-900 max-w-md"
                    />
                    <Button onClick={handleSearch}>Buscar</Button>
                    <select
                        className="px-3 py-2 border border-gray-300 rounded-md text-gray-900 bg-white"
                        value={filtroStatus}
                        onChange={(e) => setFiltroStatus(e.target.value)}
                    >
                        {statusOptions.map(option => (
                            <option key={option.value} value={option.value}>
                                {option.label}
                            </option>
                        ))}
                    </select>
                    {searchTerm && (
                        <Button
                            onClick={() => {
                                setSearch('')
                                setSearchTerm('')
                                setFiltroStatus('TODAS')
                            }}
                        >
                            Limpar
                        </Button>
                    )}
                </div>
                <Button href="/ordens-producao/novo">Nova OP</Button>
            </div>

            {!ordensFiltradas?.length ? (
                <div className="text-center py-12 text-gray-400">
                    Nenhuma ordem de produção encontrada.
                </div>
            ) : (
                <Card>
                    <Table>
                        <thead>
                            <tr>
                                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Número</th>
                                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Produto</th>
                                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Quantidade</th>
                                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Data Emissão</th>
                                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Prevista</th>
                                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
                                <th className="px-4 py-3 text-left text-xs font-medium text-gray-500 uppercase">Ações</th>
                            </tr>
                        </thead>
                        <tbody className="bg-white divide-y divide-gray-200">
                            {ordensFiltradas.map((op) => (
                                <tr key={op.id} className="hover:bg-gray-50">
                                    <td className="px-4 py-3 whitespace-nowrap font-mono text-sm text-gray-900">
                                        {op.numeroOp}
                                    </td>
                                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900">
                                        {op.produto.nome}
                                    </td>
                                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900">
                                        {op.quantidadePlanejada}
                                    </td>
                                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900">
                                        {new Date(op.dataEmissao).toLocaleDateString('pt-BR')}
                                    </td>
                                    <td className="px-4 py-3 whitespace-nowrap text-sm text-gray-900">
                                        {op.dataFimPrevista ? new Date(op.dataFimPrevista).toLocaleDateString('pt-BR') : '-'}
                                    </td>
                                    <td className="px-4 py-3 whitespace-nowrap text-sm">
                                        <span className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${getStatusClasses(op.status)}`}>
                                            {op.status.replace('_', ' ')}
                                        </span>
                                    </td>
                                    <td className="px-4 py-3 whitespace-nowrap text-sm space-x-2">
                                        <Link href={`/ordens-producao/${op.id}`}>
                                            <Button size="sm">Detalhes</Button>
                                        </Link>

                                        {op.status === 'PLANEJADA' && (
                                            <Button
                                                size="sm"
                                                onClick={() => handleIniciar(op.id, op.numeroOp)}
                                            >
                                                Iniciar
                                            </Button>
                                        )}

                                        {op.status === 'EM_PRODUCAO' && (
                                            <>
                                                <Button
                                                    size="sm"
                                                    onClick={() => handlePausar(op.id, op.numeroOp)}
                                                >
                                                    Pausar
                                                </Button>
                                                <Button
                                                    size="sm"
                                                    onClick={() => handleConcluir(op.id, op.numeroOp)}
                                                >
                                                    Concluir
                                                </Button>
                                            </>
                                        )}

                                        {op.status === 'PAUSADA' && (
                                            <Button
                                                size="sm"
                                                onClick={() => handleIniciar(op.id, op.numeroOp)}
                                            >
                                                Retomar
                                            </Button>
                                        )}

                                        {(op.status === 'PLANEJADA' || op.status === 'PAUSADA') && (
                                            <Button
                                                size="sm"
                                                onClick={() => handleCancelar(op.id, op.numeroOp)}
                                            >
                                                Cancelar
                                            </Button>
                                        )}
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                </Card>
            )}
        </div>
    )
}