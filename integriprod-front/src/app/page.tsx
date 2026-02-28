'use client'

import { useProdutos, usePessoas, useOrdens, useEstoque } from '@/hooks/useApi'
import { Card, Button, Loading } from '@/components/ui'
import Link from 'next/link'
import {
    CubeIcon,
    UsersIcon,
    ClipboardDocumentListIcon,
    ArchiveBoxIcon,
    ArrowRightIcon
} from '@heroicons/react/24/outline'

export default function HomePage() {
    const { data: produtos, isLoading: loadingProdutos } = useProdutos()
    const { data: pessoas, isLoading: loadingPessoas } = usePessoas()
    const { data: ordens, isLoading: loadingOrdens } = useOrdens()
    const { data: estoque, isLoading: loadingEstoque } = useEstoque()

    const isLoading = loadingProdutos || loadingPessoas || loadingOrdens || loadingEstoque

    if (isLoading) return <Loading fullScreen />

    const stats = [
        {
            name: 'Produtos',
            value: produtos?.length || 0,
            icon: CubeIcon,
            href: '/produtos',
            color: 'bg-blue-500',
            bgColor: 'bg-blue-50'
        },
        {
            name: 'Pessoas',
            value: pessoas?.length || 0,
            icon: UsersIcon,
            href: '/pessoas',
            color: 'bg-green-500',
            bgColor: 'bg-green-50'
        },
        {
            name: 'Ordens de Produção',
            value: ordens?.length || 0,
            icon: ClipboardDocumentListIcon,
            href: '/ordens-producao',
            color: 'bg-purple-500',
            bgColor: 'bg-purple-50'
        },
        {
            name: 'Itens em Estoque',
            value: estoque?.length || 0,
            icon: ArchiveBoxIcon,
            href: '/estoque',
            color: 'bg-amber-500',
            bgColor: 'bg-amber-50'
        }
    ]

    const ordensAbertas = ordens?.filter(o =>
        o.status === 'EM_PRODUCAO' || o.status === 'PLANEJADA' || o.status === 'LIBERADA'
    ).length || 0

    return (
        <>
            <header className="bg-white border-b border-gray-200 sticky top-0 z-10">
                <div className="px-6 py-4">
                    <div className="flex justify-between items-center">
                        <div className="flex items-center gap-4">
                            <Link href="/" className="flex items-center gap-3">
                                <div className="w-10 h-10 bg-blue-600 rounded-lg flex items-center justify-center text-white font-bold text-xl">
                                    I
                                </div>
                                <span className="text-xl font-semibold text-gray-900">INOVS</span>
                            </Link>
                            <span className="text-sm text-gray-500 hidden sm:inline">Sistema ERP</span>
                        </div>

                        <div className="flex items-center gap-4">
                            <span className="text-sm text-gray-500 hidden md:inline">Cliente:</span>
                            <div className="w-8 h-8 bg-gray-200 rounded-full flex items-center justify-center">
                                <span className="text-xs text-gray-600">C</span>
                            </div>
                            <span className="text-sm font-medium text-gray-700">Empresa Cliente</span>
                        </div>
                    </div>
                </div>
            </header>

            <main className="p-6">
                <div className="mb-8 flex justify-between items-center">
                    <div>
                        <h1 className="text-2xl font-bold text-white">Dashboard</h1>
                        <p className="text-gray-200">
                            {new Date().toLocaleDateString('pt-BR', {
                                weekday: 'long',
                                year: 'numeric',
                                month: 'long',
                                day: 'numeric'
                            })}
                        </p>
                    </div>

                    {ordensAbertas > 0 && (
                        <div className="flex gap-2">
                            <span className="bg-yellow-100 text-yellow-800 text-sm font-medium px-3 py-1 rounded-full">
                                {ordensAbertas} ordem(ns) em andamento
                            </span>
                        </div>
                    )}
                </div>

                {/* Menu de Navegação Principal */}
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
                    <Link href="/produtos">
                        <div className="bg-white p-6 rounded-xl hover:shadow-lg transition-all hover:scale-105 cursor-pointer border border-gray-100">
                            <CubeIcon className="h-8 w-8 text-blue-600 mb-2" />
                            <h2 className="text-lg font-semibold text-gray-900">Produtos</h2>
                            <p className="text-sm text-gray-600">Gerenciar catálogo de produtos</p>
                        </div>
                    </Link>
                    <Link href="/pessoas">
                        <div className="bg-white p-6 rounded-xl hover:shadow-lg transition-all hover:scale-105 cursor-pointer border border-gray-100">
                            <UsersIcon className="h-8 w-8 text-green-600 mb-2" />
                            <h2 className="text-lg font-semibold text-gray-900">Pessoas</h2>
                            <p className="text-sm text-gray-600">Clientes, fornecedores e funcionários</p>
                        </div>
                    </Link>
                    <Link href="/ordens-producao">
                        <div className="bg-white p-6 rounded-xl hover:shadow-lg transition-all hover:scale-105 cursor-pointer border border-gray-100">
                            <ClipboardDocumentListIcon className="h-8 w-8 text-purple-600 mb-2" />
                            <h2 className="text-lg font-semibold text-gray-900">Ordens</h2>
                            <p className="text-sm text-gray-600">Ordens de produção</p>
                        </div>
                    </Link>
                    <Link href="/estoque">
                        <div className="bg-white p-6 rounded-xl hover:shadow-lg transition-all hover:scale-105 cursor-pointer border border-gray-100">
                            <ArchiveBoxIcon className="h-8 w-8 text-amber-600 mb-2" />
                            <h2 className="text-lg font-semibold text-gray-900">Estoque</h2>
                            <p className="text-sm text-gray-600">Controle de estoque</p>
                        </div>
                    </Link>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
                    {stats.map((item) => (
                        <Link href={item.href} key={item.name}>
                            <div className={`${item.bgColor} rounded-xl p-6 hover:shadow-lg transition-all hover:scale-105 cursor-pointer border border-gray-100`}>
                                <div className="flex items-center justify-between">
                                    <div>
                                        <p className="text-sm font-medium text-gray-600 mb-1">{item.name}</p>
                                        <p className="text-3xl font-bold text-gray-900">{item.value}</p>
                                    </div>
                                    <div className={`${item.color} p-3 rounded-xl text-white shadow-lg`}>
                                        <item.icon className="h-6 w-6" />
                                    </div>
                                </div>
                                <div className="mt-4 flex items-center text-sm text-gray-500">
                                    <span>Ver todos</span>
                                    <ArrowRightIcon className="h-4 w-4 ml-1" />
                                </div>
                            </div>
                        </Link>
                    ))}
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-8">
                    <div className="lg:col-span-2">
                        <Card title="Ações Rápidas">
                            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                                <Button
                                    href="/produtos/novo"
                                    variant="secondary"
                                    className="h-24 flex-col hover:border-blue-500 hover:text-blue-600"
                                >
                                    <span className="text-2xl mb-1">+</span>
                                    <span className="text-sm font-medium">Novo Produto</span>
                                </Button>
                                <Button
                                    href="/pessoas/novo"
                                    variant="secondary"
                                    className="h-24 flex-col hover:border-green-500 hover:text-green-600"
                                >
                                    <span className="text-2xl mb-1">+</span>
                                    <span className="text-sm font-medium">Nova Pessoa</span>
                                </Button>
                                <Button
                                    href="/ordens-producao/novo"
                                    variant="secondary"
                                    className="h-24 flex-col hover:border-purple-500 hover:text-purple-600"
                                >
                                    <span className="text-2xl mb-1">+</span>
                                    <span className="text-sm font-medium">Nova OP</span>
                                </Button>
                                <Button
                                    href="/estoque/entrada"
                                    variant="secondary"
                                    className="h-24 flex-col hover:border-amber-500 hover:text-amber-600"
                                >
                                    <span className="text-2xl mb-1">↗</span>
                                    <span className="text-sm font-medium">Entrada</span>
                                </Button>
                            </div>
                        </Card>
                    </div>

                    <Card title="Resumo do Sistema">
                        <div className="space-y-4">
                            <div className="flex justify-between items-center">
                                <span className="text-gray-600">Total de Registros:</span>
                                <span className="font-bold text-gray-900">
                                    {(produtos?.length || 0) + (pessoas?.length || 0) + (ordens?.length || 0) + (estoque?.length || 0)}
                                </span>
                            </div>
                            <div className="flex justify-between items-center">
                                <span className="text-gray-600">Produtos Ativos:</span>
                                <span className="font-bold text-gray-900">
                                    {produtos?.filter(p => p.status === 'ATIVO').length || 0}
                                </span>
                            </div>
                            <div className="flex justify-between items-center">
                                <span className="text-gray-600">Pessoas Ativas:</span>
                                <span className="font-bold text-gray-900">
                                    {pessoas?.filter(p => p.situacao === 'ATIVO').length || 0}
                                </span>
                            </div>
                        </div>
                    </Card>
                </div>

                <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <Card title="Últimos Produtos">
                        <div className="space-y-3">
                            {produtos?.slice(0, 5).map((produto) => (
                                <Link href={`/produtos/${produto.id}`} key={produto.id}>
                                    <div className="flex justify-between items-center p-2 hover:bg-gray-50 rounded-lg transition-colors">
                                        <div>
                                            <p className="font-medium text-gray-900">{produto.nome}</p>
                                            <p className="text-sm text-gray-600">Código: {produto.codigo}</p>
                                        </div>
                                        <span className="text-sm text-gray-500">{produto.tipoProduto.descricao}</span>
                                    </div>
                                </Link>
                            ))}
                        </div>
                    </Card>

                    <Card title="Últimas Pessoas">
                        <div className="space-y-3">
                            {pessoas?.slice(0, 5).map((pessoa) => (
                                <Link href={`/pessoas/${pessoa.id}`} key={pessoa.id}>
                                    <div className="flex justify-between items-center p-2 hover:bg-gray-50 rounded-lg transition-colors">
                                        <div>
                                            <p className="font-medium text-gray-900">{pessoa.nomeRazao}</p>
                                            <p className="text-sm text-gray-600">{pessoa.email || 'Sem email'}</p>
                                        </div>
                                        <span className="text-sm text-gray-500">{pessoa.tipoPessoa}</span>
                                    </div>
                                </Link>
                            ))}
                        </div>
                    </Card>
                </div>
            </main>
        </>
    )
}