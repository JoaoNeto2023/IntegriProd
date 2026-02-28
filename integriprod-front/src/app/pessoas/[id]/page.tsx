'use client'

import { useState, useEffect } from 'react'
import { useRouter, useParams } from 'next/navigation'
import {
    usePessoa,
    useAtualizarPessoa,
    useEnderecosPessoa,
    useClassificacoesPessoa
} from '@/hooks/useApi'
import { Button, Input, Select, Card, Loading, Badge } from '@/components/ui'
import toast from 'react-hot-toast'
import type { TipoPessoa, SituacaoPessoa } from '@/types/pessoas'

export default function EditarPessoaPage() {
    const router = useRouter()
    const params = useParams()
    const id = Number(params.id)

    const [activeTab, setActiveTab] = useState('dados')
    const [tipo, setTipo] = useState<TipoPessoa>('PF')
    const [formData, setFormData] = useState({
        nomeRazao: '',
        nomeFantasia: '',
        cpfCnpj: '',
        rgIe: '',
        dataNascimento: '',
        email: '',
        telefone1: '',
        celular: '',
        uf: '',
        cidade: '',
        situacao: 'ATIVO' as SituacaoPessoa
    })

    const { data: pessoa, isLoading: loadingPessoa } = usePessoa(id)
    const { data: enderecos } = useEnderecosPessoa(id)
    const { data: classificacoes } = useClassificacoesPessoa(id)
    const { mutate: atualizarPessoa, isPending } = useAtualizarPessoa(id)

    useEffect(() => {
        if (pessoa) {
            setTipo(pessoa.tipoPessoa)
            setFormData({
                nomeRazao: pessoa.nomeRazao,
                nomeFantasia: pessoa.nomeFantasia || '',
                cpfCnpj: pessoa.cpfCnpj,
                rgIe: pessoa.rgIe || '',
                dataNascimento: pessoa.dataNascimento || '',
                email: pessoa.email || '',
                telefone1: pessoa.telefone1 || '',
                celular: pessoa.celular || '',
                uf: pessoa.uf || '',
                cidade: pessoa.cidade || '',
                situacao: pessoa.situacao
            })
        }
    }, [pessoa])

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        e.stopPropagation()

        if (!formData.nomeRazao || !formData.cpfCnpj) {
            toast.error('Preencha os campos obrigatórios')
            return
        }

        const payload = {
            tipoPessoa: tipo,
            nomeRazao: formData.nomeRazao,
            nomeFantasia: formData.nomeFantasia || undefined,
            cpfCnpj: formData.cpfCnpj.replace(/\D/g, ''),
            rgIe: formData.rgIe || undefined,
            dataNascimento: formData.dataNascimento || undefined,
            email: formData.email || undefined,
            telefone1: formData.telefone1 || undefined,
            celular: formData.celular || undefined,
            uf: formData.uf || undefined,
            cidade: formData.cidade || undefined,
            situacao: formData.situacao
        }

        atualizarPessoa(payload, {
            onSuccess: () => {
                toast.success('Pessoa atualizada com sucesso!')
                router.push('/pessoas')
            },
            onError: (error: any) => {
                toast.error(error.response?.data?.message || 'Erro ao atualizar pessoa')
            }
        })
    }

    const handleCpfCnpjChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replace(/\D/g, '')

        if (tipo === 'PF') {
            if (value.length <= 11) {
                value = value.replace(/(\d{3})(\d)/, '$1.$2')
                value = value.replace(/(\d{3})(\d)/, '$1.$2')
                value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2')
            }
        } else {
            if (value.length <= 14) {
                value = value.replace(/(\d{2})(\d)/, '$1.$2')
                value = value.replace(/(\d{3})(\d)/, '$1.$2')
                value = value.replace(/(\d{3})(\d{1,2})$/, '$1/$2')
                value = value.replace(/(\d{4})(\d{2})$/, '$1-$2')
            }
        }

        setFormData({ ...formData, cpfCnpj: value })
    }

    if (loadingPessoa) return <Loading fullScreen />

    return (
        <div className="p-6 max-w-2xl mx-auto">
            <Card title="Editar Pessoa">
                <div className="mb-4 border-b border-gray-200">
                    <nav className="flex gap-4">
                        <button
                            className={`pb-2 px-1 text-sm font-medium transition-colors ${activeTab === 'dados'
                                ? 'border-b-2 border-blue-600 text-blue-600'
                                : 'text-gray-500 hover:text-gray-700'
                                }`}
                            onClick={() => setActiveTab('dados')}
                        >
                            Dados Pessoais
                        </button>
                        <button
                            className={`pb-2 px-1 text-sm font-medium transition-colors ${activeTab === 'enderecos'
                                ? 'border-b-2 border-blue-600 text-blue-600'
                                : 'text-gray-500 hover:text-gray-700'
                                }`}
                            onClick={() => setActiveTab('enderecos')}
                        >
                            Endereços {enderecos && enderecos.length > 0 && `(${enderecos.length})`}
                        </button>
                        <button
                            className={`pb-2 px-1 text-sm font-medium transition-colors ${activeTab === 'classificacoes'
                                ? 'border-b-2 border-blue-600 text-blue-600'
                                : 'text-gray-500 hover:text-gray-700'
                                }`}
                            onClick={() => setActiveTab('classificacoes')}
                        >
                            Classificações {classificacoes && classificacoes.length > 0 && `(${classificacoes.length})`}
                        </button>
                    </nav>
                </div>

                {activeTab === 'dados' && (
                    <form onSubmit={handleSubmit} className="space-y-4" noValidate>
                        <div className="flex gap-4">
                            <label className="flex items-center text-gray-700">
                                <input
                                    type="radio"
                                    value="PF"
                                    checked={tipo === 'PF'}
                                    onChange={(e) => {
                                        setTipo('PF')
                                        setFormData({ ...formData, cpfCnpj: '' })
                                    }}
                                    className="mr-2"
                                />
                                Pessoa Física
                            </label>
                            <label className="flex items-center text-gray-700">
                                <input
                                    type="radio"
                                    value="PJ"
                                    checked={tipo === 'PJ'}
                                    onChange={(e) => {
                                        setTipo('PJ')
                                        setFormData({ ...formData, cpfCnpj: '' })
                                    }}
                                    className="mr-2"
                                />
                                Pessoa Jurídica
                            </label>
                        </div>

                        <Input
                            label={tipo === 'PF' ? 'Nome Completo *' : 'Razão Social *'}
                            value={formData.nomeRazao}
                            onChange={(e) => setFormData({ ...formData, nomeRazao: e.target.value })}
                            required
                            className="text-gray-900"
                        />

                        {tipo === 'PJ' && (
                            <Input
                                label="Nome Fantasia"
                                value={formData.nomeFantasia}
                                onChange={(e) => setFormData({ ...formData, nomeFantasia: e.target.value })}
                                className="text-gray-900"
                            />
                        )}

                        <Input
                            label={tipo === 'PF' ? 'CPF *' : 'CNPJ *'}
                            value={formData.cpfCnpj}
                            onChange={handleCpfCnpjChange}
                            placeholder={tipo === 'PF' ? '000.000.000-00' : '00.000.000/0000-00'}
                            maxLength={tipo === 'PF' ? 14 : 18}
                            required
                            className="text-gray-900"
                        />

                        <Input
                            label={tipo === 'PF' ? 'RG' : 'Inscrição Estadual'}
                            value={formData.rgIe}
                            onChange={(e) => setFormData({ ...formData, rgIe: e.target.value })}
                            className="text-gray-900"
                        />

                        {tipo === 'PF' && (
                            <Input
                                label="Data de Nascimento"
                                type="date"
                                value={formData.dataNascimento}
                                onChange={(e) => setFormData({ ...formData, dataNascimento: e.target.value })}
                                className="text-gray-900"
                            />
                        )}

                        <Input
                            label="Email"
                            type="email"
                            value={formData.email}
                            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                            className="text-gray-900"
                        />

                        <div className="grid grid-cols-2 gap-4">
                            <Input
                                label="Telefone"
                                value={formData.telefone1}
                                onChange={(e) => setFormData({ ...formData, telefone1: e.target.value })}
                                placeholder="(00) 0000-0000"
                                className="text-gray-900"
                            />
                            <Input
                                label="Celular"
                                value={formData.celular}
                                onChange={(e) => setFormData({ ...formData, celular: e.target.value })}
                                placeholder="(00) 00000-0000"
                                className="text-gray-900"
                            />
                        </div>

                        <div className="grid grid-cols-2 gap-4">
                            <Input
                                label="UF"
                                value={formData.uf}
                                onChange={(e) => setFormData({ ...formData, uf: e.target.value.toUpperCase() })}
                                maxLength={2}
                                placeholder="SP"
                                className="text-gray-900"
                            />
                            <Input
                                label="Cidade"
                                value={formData.cidade}
                                onChange={(e) => setFormData({ ...formData, cidade: e.target.value })}
                                className="text-gray-900"
                            />
                        </div>

                        <Select
                            label="Situação"
                            options={[
                                { value: 'ATIVO', label: 'Ativo' },
                                { value: 'INATIVO', label: 'Inativo' }
                            ]}
                            value={formData.situacao}
                            onChange={(e) => setFormData({
                                ...formData,
                                situacao: e.target.value as SituacaoPessoa
                            })}
                            className="text-gray-900"
                        />

                        <div className="flex justify-end gap-3 pt-4">
                            <Button
                                variant="ghost"
                                onClick={() => router.push('/pessoas')}
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
                )}

                {activeTab === 'enderecos' && (
                    <div className="space-y-4">
                        <div className="flex justify-between items-center">
                            <h3 className="text-lg font-medium text-gray-900">Endereços</h3>
                            <Button size="sm">Novo Endereço</Button>
                        </div>
                        {enderecos?.length === 0 ? (
                            <p className="text-gray-500">Nenhum endereço cadastrado.</p>
                        ) : (
                            enderecos?.map((endereco) => (
                                <div key={endereco.id} className="border border-gray-200 p-4 rounded-lg">
                                    <div className="flex justify-between mb-2">
                                        <span className="text-sm font-medium text-gray-700">{endereco.tipoEndereco}</span>
                                        {endereco.principal && <Badge variant="success">Principal</Badge>}
                                    </div>
                                    <p className="text-gray-900">{endereco.logradouro}, {endereco.numero} {endereco.complemento}</p>
                                    <p className="text-gray-700">{endereco.bairro} - {endereco.cidade}/{endereco.uf}</p>
                                    <p className="text-gray-600 text-sm">CEP: {endereco.cep}</p>
                                </div>
                            ))
                        )}
                    </div>
                )}

                {activeTab === 'classificacoes' && (
                    <div className="space-y-4">
                        <div className="flex justify-between items-center">
                            <h3 className="text-lg font-medium text-gray-900">Classificações</h3>
                            <Button size="sm">Nova Classificação</Button>
                        </div>
                        {classificacoes?.length === 0 ? (
                            <p className="text-gray-500">Nenhuma classificação cadastrada.</p>
                        ) : (
                            classificacoes?.map((classificacao) => (
                                <div key={classificacao.tipo} className="border border-gray-200 p-4 rounded-lg">
                                    <Badge variant="info" className="mb-2">{classificacao.tipo}</Badge>
                                    {classificacao.limiteCredito && (
                                        <p className="text-gray-900">Limite: R$ {classificacao.limiteCredito.toFixed(2)}</p>
                                    )}
                                    {classificacao.diasPrazo && (
                                        <p className="text-gray-700">Prazo: {classificacao.diasPrazo} dias</p>
                                    )}
                                </div>
                            ))
                        )}
                    </div>
                )}
            </Card>
        </div>
    )
}