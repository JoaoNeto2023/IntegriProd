import Link from 'next/link'
import { ReactNode, ButtonHTMLAttributes } from 'react'

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    children: ReactNode
    href?: string
    variant?: 'primary' | 'secondary' | 'danger' | 'ghost'
    size?: 'sm' | 'md' | 'lg'
}

export function Button({
    children,
    href,
    variant = 'primary',
    size = 'md',
    className = '',
    type = 'button',
    ...props
}: ButtonProps) {
    const base = 'inline-flex items-center justify-center font-medium rounded-md transition-colors'

    const variants = {
        primary: 'bg-blue-600 text-white hover:bg-blue-700 disabled:bg-blue-300',
        secondary: 'bg-gray-200 text-gray-900 hover:bg-gray-300 disabled:bg-gray-100',
        danger: 'bg-red-600 text-white hover:bg-red-700 disabled:bg-red-300',
        ghost: 'bg-transparent text-gray-600 hover:bg-gray-100 disabled:text-gray-400'
    }

    const sizes = {
        sm: 'px-3 py-1.5 text-sm',
        md: 'px-4 py-2 text-base',
        lg: 'px-6 py-3 text-lg'
    }

    const classes = `${base} ${variants[variant]} ${sizes[size]} ${className}`

    if (href) {
        return <Link href={href} className={classes}>{children}</Link>
    }

    return (
        <button
            type={type}
            className={classes}
            {...props}
        >
            {children}
        </button>
    )
}