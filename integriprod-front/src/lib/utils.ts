import { clsx, type ClassValue } from 'clsx'
import { twMerge } from 'tailwind-merge'

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs))
}

export function formatCurrency(value: number) {
    return new Intl.NumberFormat('pt-BR', {
        style: 'currency',
        currency: 'BRL',
    }).format(value)
}

export function formatDate(dateStr: string) {
    return new Date(dateStr).toLocaleDateString('pt-BR')
}

export function formatDateTime(dateStr: string) {
    return new Date(dateStr).toLocaleString('pt-BR')
}