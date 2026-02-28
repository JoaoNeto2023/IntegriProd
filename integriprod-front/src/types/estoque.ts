import type { Produto } from './produtos'
import type { Filial } from './organizacao'

// ─── Estoque ───────────────────────────────────────────────────────────────────
export type StatusLote = 'ATIVO' | 'BLOQUEADO' | 'VENCIDO' | 'QUARENTENA'

export interface Estoque {
    id: number
    filial: Pick<Filial, 'id' | 'nome'>
    produto: Pick<Produto, 'id' | 'nome' | 'codigo'>
    lote?: string
    quantidade: number
    localizacao?: string
    custoUnitario?: number
    statusLote: StatusLote
    dataFabricacao?: string
    dataValidade?: string
    unidadeMedida?: { sigla: string; descricao: string }
}

export interface EstoquePayload {
    filial: { id: number }
    produto: { id: number }
    lote?: string
    quantidade: number
    localizacao?: string
    custoUnitario?: number
    statusLote: StatusLote
    disponivel?: number
}

// ─── Movimentos ────────────────────────────────────────────────────────────────
export type TipoMovimento = 'ENTRADA' | 'SAIDA' | 'TRANSFERENCIA' | 'AJUSTE'

export interface MovimentoEstoque {
    id: number
    tipoMovimento: TipoMovimento
    filial: Pick<Filial, 'id' | 'nome'>
    produto: Pick<Produto, 'id' | 'nome' | 'codigo'>
    quantidade: number
    loteOrigem?: string
    loteDestino?: string
    documentoTipo?: string
    documentoNumero?: string
    usuarioMovimento: string
    dataMovimento: string
    observacao?: string
}

export interface EntradaPayload {
    filial: { id: number }
    produto: { id: number }
    quantidade: number
    loteDestino?: string
    documentoTipo?: string
    documentoNumero?: string
    usuarioMovimento: string
}

export interface SaidaPayload {
    filial: { id: number }
    produto: { id: number }
    quantidade: number
    loteOrigem?: string
    documentoTipo?: string
    documentoNumero?: string
    usuarioMovimento: string
}

export interface TransferenciaPayload {
    filial: { id: number }
    produto: { id: number }
    quantidade: number
    loteOrigem: string
    loteDestino: string
    usuarioMovimento: string
}

export interface AjustePayload {
    filial: { id: number }
    produto: { id: number }
    quantidade: number
    loteOrigem?: string
    usuarioMovimento: string
    observacao?: string
}