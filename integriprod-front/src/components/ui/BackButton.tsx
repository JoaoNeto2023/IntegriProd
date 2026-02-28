'use client'

import { useRouter } from 'next/navigation'
import { Button } from './Button'
import { ArrowLeftIcon } from '@heroicons/react/24/outline'

interface BackButtonProps {
    fallbackRoute?: string
    label?: string
}

export function BackButton({ fallbackRoute = '/', label = 'Voltar' }: BackButtonProps) {
    const router = useRouter()

    const handleBack = () => {
        // Tenta voltar para a página anterior, se não houver histórico, vai para o fallback
        if (window.history.length > 1) {
            router.back()
        } else {
            router.push(fallbackRoute)
        }
    }

    return (
        <Button
            variant="ghost"
            onClick={handleBack}
            className="flex items-center gap-2"
        >
            <ArrowLeftIcon className="h-4 w-4" />
            {label}
        </Button>
    )
}