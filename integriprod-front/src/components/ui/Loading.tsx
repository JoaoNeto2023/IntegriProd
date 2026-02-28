interface LoadingProps {
    size?: 'sm' | 'md' | 'lg'
    fullScreen?: boolean
}

export function Loading({ size = 'md', fullScreen = false }: LoadingProps) {
    const sizes = {
        sm: 'h-4 w-4',
        md: 'h-8 w-8',
        lg: 'h-12 w-12'
    }

    const spinner = (
        <div className="flex justify-center items-center">
            <div className={`
                ${sizes[size]} 
                border-2 border-gray-200 
                border-t-blue-600 rounded-full 
                animate-spin
            `} />
        </div>
    )

    if (fullScreen) {
        return (
            <div className="fixed inset-0 bg-white bg-opacity-75 flex items-center justify-center z-50">
                {spinner}
            </div>
        )
    }

    return spinner
}