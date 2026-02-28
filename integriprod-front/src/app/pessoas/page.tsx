'use client'

import { useState } from 'react'
import { usePessoas, useExcluirPessoa } from '@/hooks/useApi'
import { Button, Input, Table, Badge, Loading } from '@/components/ui'
import toast from 'react-hot-toast'

export default function PessoasPage() {
    const [search, setSearch] = useState('')
    const [searchTerm, setSearchTerm] = useState('')
    const [filtroTipo, setFiltroTipo] = useState<'TODAS' | 'PF' | 'PJ'>('TODAS')

    const { data: pessoas, isLoading, refetch } = usePessoas()
    const { mutate: excluirPessoa } = useExcluirPessoa()

    const handleSearch = () => {
        setSearchTerm(search)
        if (!search) refetch()
    }

    const pessoasFiltradas = pessoas?.filter(p => {
        const matchSearch = searchTerm
            ? p.nomeRazao.toLowerCase().includes(searchTerm.toLowerCase()) ||
            p.cpfCnpj.includes(searchTerm)
            : true

        const matchTipo = filtroTipo === 'TODAS' ? true : p.tipoPessoa === filtroTipo

        return matchSearch && matchTipo
    })

    const handleExcluir = (id: number, nome: string) => {
        if (confirm(`Deseja realmente excluir "${nome}"?`)) {
            excluirPessoa(id, {
                onSuccess: () => {
                    toast.success('Pessoa excluída com sucesso!')
                    refetch()
                },
                onError: (error: any) => {
                    toast.error(error.response?.data?.message || 'Erro ao excluir')
                }
            })
        }
    }

    if (isLoading) return <Loading fullScreen />

    return (
        <div className="p-6">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold text-gray-900">Pessoas</h1> {/* ← Título mais escuro */}
                <Button href="/pessoas/novo">Nova Pessoa</Button>
            </div>

            <div className="flex gap-4 mb-6">
                <Input
                    placeholder="Buscar por nome ou CPF/CNPJ..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    onKeyDown={(e) => e.key === 'Enter' && handleSearch()}
                    className="text-gray-900"
                />
                <Button onClick={handleSearch} variant="secondary">
                    Buscar
                </Button>

                <select
                    className="px-3 py-2 border border-gray-300 rounded-md text-gray-900 bg-white"
                    value={filtroTipo}
                    onChange={(e) => setFiltroTipo(e.target.value as any)}
                >
                    <option value="TODAS" className="text-gray-900">Todos</option>
                    <option value="PF" className="text-gray-900">Pessoa Física</option>
                    <option value="PJ" className="text-gray-900">Pessoa Jurídica</option>
                </select>

                {searchTerm && (
                    <Button
                        variant="ghost"
                        onClick={() => {
                            setSearch('')
                            setSearchTerm('')
                            setFiltroTipo('TODAS')
                            refetch()
                        }}
                    >
                        Limpar
                    </Button>
                )}
            </div>

            {!pessoasFiltradas?.length ? (
                <div className="text-center py-12 text-gray-500">
                    Nenhuma pessoa encontrada.
                </div>
            ) : (
                <Table>
                    <thead>
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase tracking-wider">Nome/Razão</th> {/* ← Cabeçalho mais escuro */}
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase tracking-wider">Tipo</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase tracking-wider">CPF/CNPJ</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase tracking-wider">Email</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase tracking-wider">Contato</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase tracking-wider">Status</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-700 uppercase tracking-wider">Ações</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white divide-y divide-gray-200">
                        {pessoasFiltradas.map((pessoa) => (
                            <tr key={pessoa.id} className="hover:bg-gray-50">
                                <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900"> {/* ← Nome em preto */}
                                    {pessoa.nomeRazao}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm">
                                    <Badge variant={pessoa.tipoPessoa === 'PF' ? 'info' : 'warning'}>
                                        {pessoa.tipoPessoa}
                                    </Badge>
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm font-mono text-gray-900"> {/* ← CPF/CNPJ em preto */}
                                    {pessoa.cpfCnpj}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900"> {/* ← Email em preto */}
                                    {pessoa.email || '-'}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900"> {/* ← Contato em preto */}
                                    {pessoa.celular || '-'}
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm">
                                    <Badge variant={pessoa.situacao === 'ATIVO' ? 'success' : 'danger'}>
                                        {pessoa.situacao}
                                    </Badge>
                                </td>
                                <td className="px-6 py-4 whitespace-nowrap text-sm space-x-2">
                                    <Button href={`/pessoas/${pessoa.id}`} size="sm">
                                        Editar
                                    </Button>
                                    <Button
                                        size="sm"
                                        variant="danger"
                                        onClick={() => handleExcluir(pessoa.id, pessoa.nomeRazao)}
                                    >
                                        Excluir
                                    </Button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            )}
        </div>
    )
}