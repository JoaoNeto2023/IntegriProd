'use client'

import { ReactNode, useState } from 'react'

interface Tab {
    id: string
    label: string
    content: ReactNode
    badge?: number
}

interface TabsProps {
    tabs: Tab[]
    defaultTab?: string
    onChange?: (tabId: string) => void
}

export function Tabs({ tabs, defaultTab, onChange }: TabsProps) {
    const [activeTab, setActiveTab] = useState(defaultTab || tabs[0]?.id)

    const handleTabClick = (tabId: string) => {
        setActiveTab(tabId)
        onChange?.(tabId)
    }

    return (
        <div className="w-full">
            <div className="border-b border-gray-200">
                <nav className="flex gap-6" aria-label="Tabs">
                    {tabs.map((tab) => (
                        <button
                            key={tab.id}
                            onClick={() => handleTabClick(tab.id)}
                            className={`
                                py-2 px-1 text-sm font-medium border-b-2 transition-colors
                                ${activeTab === tab.id
                                    ? 'border-blue-600 text-blue-600'
                                    : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                                }
                            `}
                        >
                            <span className="flex items-center gap-2">
                                {tab.label}
                                {tab.badge !== undefined && tab.badge > 0 && (
                                    <span className="bg-blue-100 text-blue-800 text-xs font-medium px-2 py-0.5 rounded-full">
                                        {tab.badge}
                                    </span>
                                )}
                            </span>
                        </button>
                    ))}
                </nav>
            </div>
            <div className="py-4">
                {tabs.find(tab => tab.id === activeTab)?.content}
            </div>
        </div>
    )
}