'use client'

import { useState } from 'react'
import { useParams, useRouter } from 'next/navigation'
import { useCriarApontamento, usePostos, useFuncionarios } from '@/hooks/useApi'
import { Button, Input, Select, Card, BackButton } from '@/components/ui'
import toast from 'react-hot-toast'

export default function NovoApontamentoPage() {
    const params = useParams()
    const router = useRouter()
    const ordemId = Number(params.id)

    const [formData, setFormData] = useState({
        dataApontamento: new Date().toISOString().split('T')[0],
        horaInicio: '',
        horaFim: '',
        postoTrabalhoId: '',
        funcionarioId: '',
        quantidadeProduzida: '',
        quantidadeRefugo: '0',
        tipoApontamento: 'PRODUCAO',
        motivoParada: '',
        observacoes: '',
        usuarioRegistro: 'admin'
    })

    const { data: postos } = usePostos()
    const { data: funcionarios } = useFuncionarios()
    const { mutate: criarApontamento, isPending } = useCriarApontamento()

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()

        if (!formData.quantidadeProduzida) {
            toast.error('Preencha a quantidade produzida')
            return
        }

        const payload: any = {
            ordemProducao: { id: ordemId },
            dataApontamento: formData.dataApontamento,
            quantidadeProduzida: Number(formData.quantidadeProduzida),
            quantidadeRefugo: Number(formData.quantidadeRefugo),
            tipoApontamento: formData.tipoApontamento,
            usuarioRegistro: formData.usuarioRegistro
        }

        if (formData.horaInicio) payload.horaInicio = formData.horaInicio
        if (formData.horaFim) payload.horaFim = formData.horaFim
        if (formData.postoTrabalhoId) {
            payload.postoTrabalho = { id: Number(formData.postoTrabalhoId) }
        }
        if (formData.funcionarioId) {
            payload.funcionario = { id: Number(formData.funcionarioId) }
        }
        if (formData.motivoParada) payload.motivoParada = formData.motivoParada
        if (formData.observacoes) payload.observacoes = formData.observacoes

        console.log('Payload:', payload)

        criarApontamento(payload, {
            onSuccess: () => {
                toast.success('Apontamento registrado com sucesso!')
                router.push(`/ordens-producao/${ordemId}`)
            },
            onError: (error: any) => {
                toast.error(error.response?.data?.message || 'Erro ao registrar apontamento')
            }
        })
    }

    return (
        <div className="p-6 max-w-2xl mx-auto">
            <div className="flex items-center gap-4 mb-6">
                <BackButton fallbackRoute={`/ordens-producao/${ordemId}`} />
                <h1 className="text-2xl font-bold text-white">Novo Apontamento</h1>
            </div>

            <Card>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="Data do Apontamento"
                            type="date"
                            value={formData.dataApontamento}
                            onChange={(e) => setFormData({ ...formData, dataApontamento: e.target.value })}
                            required
                        />
                        <Select
                            label="Tipo"
                            options={[
                                { value: 'PRODUCAO', label: 'Produção' },
                                { value: 'SETUP', label: 'Setup' },
                                { value: 'PARADA', label: 'Parada' },
                                { value: 'MANUTENCAO', label: 'Manutenção' }
                            ]}
                            value={formData.tipoApontamento}
                            onChange={(e) => setFormData({ ...formData, tipoApontamento: e.target.value })}
                        />
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="Hora Início"
                            type="time"
                            value={formData.horaInicio}
                            onChange={(e) => setFormData({ ...formData, horaInicio: e.target.value })}
                        />
                        <Input
                            label="Hora Fim"
                            type="time"
                            value={formData.horaFim}
                            onChange={(e) => setFormData({ ...formData, horaFim: e.target.value })}
                        />
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        <Select
                            label="Posto de Trabalho"
                            options={postos?.map(p => ({ value: String(p.id), label: p.nome })) || []}
                            value={formData.postoTrabalhoId}
                            onChange={(e) => setFormData({ ...formData, postoTrabalhoId: e.target.value })}
                        />
                        <Select
                            label="Funcionário"
                            options={funcionarios?.map(f => ({ value: String(f.id), label: f.nomeRazao })) || []}
                            value={formData.funcionarioId}
                            onChange={(e) => setFormData({ ...formData, funcionarioId: e.target.value })}
                        />
                    </div>

                    <div className="grid grid-cols-2 gap-4">
                        <Input
                            label="Quantidade Produzida *"
                            type="number"
                            step="0.01"
                            value={formData.quantidadeProduzida}
                            onChange={(e) => setFormData({ ...formData, quantidadeProduzida: e.target.value })}
                            required
                        />
                        <Input
                            label="Quantidade Refugo"
                            type="number"
                            step="0.01"
                            value={formData.quantidadeRefugo}
                            onChange={(e) => setFormData({ ...formData, quantidadeRefugo: e.target.value })}
                        />
                    </div>

                    {formData.tipoApontamento === 'PARADA' && (
                        <Input
                            label="Motivo da Parada"
                            value={formData.motivoParada}
                            onChange={(e) => setFormData({ ...formData, motivoParada: e.target.value })}
                            placeholder="Descreva o motivo da parada..."
                        />
                    )}

                    <Input
                        label="Observações"
                        value={formData.observacoes}
                        onChange={(e) => setFormData({ ...formData, observacoes: e.target.value })}
                        placeholder="Observações adicionais..."
                    />

                    <div className="flex justify-end gap-3 pt-4">
                        <Button onClick={() => router.back()}>
                            Cancelar
                        </Button>
                        <Button type="submit" disabled={isPending}>
                            {isPending ? 'Salvando...' : 'Registrar Apontamento'}
                        </Button>
                    </div>
                </form>
            </Card>
        </div>
    )
}