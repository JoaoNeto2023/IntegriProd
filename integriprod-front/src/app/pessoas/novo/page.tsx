'use client'

import { useState } from 'react'
import { useRouter } from 'next/navigation'
import { useCriarPessoa } from '@/hooks/useApi'
import { Button, Input, Select, Card } from '@/components/ui'
import toast from 'react-hot-toast'
import type { TipoPessoa, SituacaoPessoa } from '@/types/pessoas'

export default function NovaPessoaPage() {
    const router = useRouter()
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

    const { mutate: criarPessoa, isPending } = useCriarPessoa()

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        e.stopPropagation()
        console.log('Submit disparado')

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

        console.log('Payload:', payload)

        criarPessoa(payload, {
            onSuccess: (data) => {
                console.log('Sucesso:', data)
                toast.success('Pessoa criada com sucesso!')
                router.push('/pessoas')
            },
            onError: (error: any) => {
                console.error('Erro:', error)
                toast.error(error.response?.data?.message || 'Erro ao criar pessoa')
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

    return (
        <div className="p-6 max-w-2xl mx-auto">
            <Card title="Nova Pessoa">
                <form
                    onSubmit={handleSubmit}
                    className="space-y-4"
                    noValidate
                >
                    <div className="flex gap-4">
                        <label className="flex items-center">
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
                        <label className="flex items-center">
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
                    />

                    {tipo === 'PJ' && (
                        <Input
                            label="Nome Fantasia"
                            value={formData.nomeFantasia}
                            onChange={(e) => setFormData({ ...formData, nomeFantasia: e.target.value })}
                        />
                    )}

                    <Input
                        label={tipo === 'PF' ? 'CPF *' : 'CNPJ *'}
                        value={formData.cpfCnpj}
                        onChange={handleCpfCnpjChange}
                        placeholder={tipo === 'PF' ? '000.000.000-00' : '00.000.000/0000-00'}
                        maxLength={tipo === 'PF' ? 14 : 18}
                        required
                    />

                    <Input
                        label={tipo === 'PF' ? 'RG' : 'Inscrição Estadual'}
                        value={formData.rgIe}
                        onChange={(e) => setFormData({ ...formData, rgIe: e.target.value })}
                    />

                    {tipo === 'PF' && (
                        <Input
                            label="Data de Nascimento"
                            type="date"
                            value={formData.dataNascimento}
                            onChange={(e) => setFormData({ ...formData, dataNascimento: e.target.value })}
                        />
                    )}

                    <Input
                        label="Email"
                        type="email"
                        value={formData.email}
                        onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                    />

                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="Telefone"
                            value={formData.telefone1}
                            onChange={(e) => setFormData({ ...formData, telefone1: e.target.value })}
                            placeholder="(00) 0000-0000"
                        />
                        <Input
                            label="Celular"
                            value={formData.celular}
                            onChange={(e) => setFormData({ ...formData, celular: e.target.value })}
                            placeholder="(00) 00000-0000"
                        />
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="UF"
                            value={formData.uf}
                            onChange={(e) => setFormData({ ...formData, uf: e.target.value.toUpperCase() })}
                            maxLength={2}
                            placeholder="SP"
                        />
                        <Input
                            label="Cidade"
                            value={formData.cidade}
                            onChange={(e) => setFormData({ ...formData, cidade: e.target.value })}
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
            </Card>
        </div>
    )
}