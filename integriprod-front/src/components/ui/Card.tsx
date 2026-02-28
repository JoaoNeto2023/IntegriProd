import { ReactNode } from 'react'

interface CardProps {
    children: ReactNode
    title?: string
    className?: string
}

export function Card({ children, title, className = '' }: CardProps) {
    return (
        <div className={`bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden ${className}`}>
            {title && (
                <div className="px-4 py-3 border-b border-gray-200 bg-gray-50">
                    <h3 className="text-sm font-semibold text-gray-700">{title}</h3>
                </div>
            )}
            <div className="p-4">{children}</div>
        </div>
    )
}