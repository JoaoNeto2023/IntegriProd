import { SelectHTMLAttributes } from 'react'

interface SelectProps extends SelectHTMLAttributes<HTMLSelectElement> {
    label?: string
    error?: string
    options: Array<{ value: string | number; label: string }>
}

export function Select({ label, error, options, className = '', ...props }: SelectProps) {
    return (
        <div className="w-full">
            {label && (
                <label className="block text-sm font-medium text-gray-700 mb-1">
                    {label}
                </label>
            )}
            <select
                className={`
                    w-full px-3 py-2 border rounded-md shadow-sm
                    focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500
                    ${error ? 'border-red-500' : 'border-gray-300'}
                    text-gray-900  // ← COR PRETA NO TEXTO SELECIONADO
                    ${className}
                `}
                {...props}
            >
                <option value="" className="text-gray-500">Selecione...</option>
                {options.map(({ value, label }) => (
                    <option key={value} value={value} className="text-gray-900">
                        {label}
                    </option>
                ))}
            </select>
            {error && <p className="mt-1 text-sm text-red-600">{error}</p>}
        </div>
    )
}