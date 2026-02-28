'use client'

import { useState } from 'react'
import { Button, Input, Select, Card, Modal, Loading, Badge } from '@/components/ui'

export default function TestePage() {
    const [modalOpen, setModalOpen] = useState(false)
    const [loading, setLoading] = useState(false)

    const options = [
        { value: 1, label: 'Opção 1' },
        { value: 2, label: 'Opção 2' },
        { value: 3, label: 'Opção 3' },
    ]

    return (
        <div className="p-6 space-y-6">
            <h1 className="text-2xl font-bold">Teste de Componentes</h1>

            <Card title="Botões">
                <div className="space-x-2">
                    <Button>Primary</Button>
                    <Button variant="secondary">Secondary</Button>
                    <Button variant="danger">Danger</Button>
                    <Button variant="ghost">Ghost</Button>
                    <Button size="sm">Small</Button>
                    <Button size="lg">Large</Button>
                </div>
            </Card>

            <Card title="Inputs">
                <div className="space-y-4 max-w-md">
                    <Input placeholder="Input normal" />
                    <Input label="Com label" placeholder="Digite algo" />
                    <Input label="Com erro" error="Campo obrigatório" />
                </div>
            </Card>

            <Card title="Select">
                <div className="max-w-md">
                    <Select
                        label="Escolha uma opção"
                        options={options}
                    />
                </div>
            </Card>

            <Card title="Badges">
                <div className="space-x-2">
                    <Badge variant="success">Sucesso</Badge>
                    <Badge variant="warning">Atenção</Badge>
                    <Badge variant="danger">Erro</Badge>
                    <Badge variant="info">Info</Badge>
                </div>
            </Card>

            <Card title="Loading">
                <div className="space-x-4">
                    <Loading size="sm" />
                    <Loading size="md" />
                    <Loading size="lg" />
                    <Button
                        onClick={() => {
                            setLoading(true)
                            setTimeout(() => setLoading(false), 2000)
                        }}
                    >
                        Simular Loading
                    </Button>
                </div>
            </Card>

            <Card title="Modal">
                <Button onClick={() => setModalOpen(true)}>
                    Abrir Modal
                </Button>
            </Card>

            <Modal
                isOpen={modalOpen}
                onClose={() => setModalOpen(false)}
                onConfirm={() => {
                    alert('Confirmado!')
                    setModalOpen(false)
                }}
                title="Confirmação"
            >
                <p>Tem certeza que deseja continuar?</p>
            </Modal>

            {loading && <Loading fullScreen />}
        </div>
    )
}