import { ReactNode } from 'react'

interface TableProps {
    children: ReactNode
    className?: string
}

export function Table({ children, className = '' }: TableProps) {
    return (
        <div className="w-full overflow-x-auto border border-gray-200 rounded-lg">
            <table className={`min-w-full divide-y divide-gray-200 ${className}`}>
                {children}
            </table>
        </div>
    )
}