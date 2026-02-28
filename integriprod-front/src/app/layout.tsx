import type { Metadata } from 'next'
import { Providers } from './providers'
import './globals.css'

export const metadata: Metadata = {
  title: 'ERP - Produção e Estoque',
  description: 'Sistema de gerenciamento de produção e estoque',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="pt-BR">
      <body style={{ backgroundColor: 'rgba(79, 11, 196, 0.5)' }}>
        <Providers>
          {children}
        </Providers>
      </body>
    </html>
  )
}