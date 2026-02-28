'use client'

import { Fragment, ReactNode } from 'react'
import { Button } from './Button'

interface ModalProps {
    isOpen: boolean
    onClose: () => void
    onConfirm?: () => void
    title: string
    children: ReactNode
    confirmText?: string
    cancelText?: string
    showConfirm?: boolean
}

export function Modal({
    isOpen,
    onClose,
    onConfirm,
    title,
    children,
    confirmText = 'Confirmar',
    cancelText = 'Cancelar',
    showConfirm = true,
}: ModalProps) {
    if (!isOpen) return null

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center">
            {/* Overlay */}
            <div
                className="fixed inset-0 bg-black bg-opacity-50 transition-opacity"
                onClick={onClose}
            />

            {/* Modal */}
            <div className="relative bg-white rounded-lg shadow-xl max-w-md w-full mx-4 p-6">
                <h3 className="text-lg font-semibold text-gray-900 mb-4">{title}</h3>

                <div className="mb-6">{children}</div>

                <div className="flex justify-end gap-3">
                    <Button variant="ghost" onClick={onClose}>
                        {cancelText}
                    </Button>
                    {showConfirm && (
                        <Button variant="primary" onClick={onConfirm}>
                            {confirmText}
                        </Button>
                    )}
                </div>
            </div>
        </div>
    )
}