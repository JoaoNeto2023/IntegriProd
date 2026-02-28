// ─── Tipo de Produto ───────────────────────────────────────────────────────────
export type NaturezaProduto = 'MP' | 'PA' | 'PI' | 'ME' | 'SE'

export interface TipoProduto {
    id: number
    codigo: string
    descricao: string
    natureza: NaturezaProduto
}

export interface TipoProdutoPayload {
    codigo: string
    descricao: string
    natureza: NaturezaProduto
}

// ─── Unidade de Medida ─────────────────────────────────────────────────────────
export interface UnidadeMedida {
    sigla: string
    descricao: string
}

export interface UnidadeMedidaPayload {
    sigla: string
    descricao: string
}

// ─── Família de Produto ────────────────────────────────────────────────────────
export interface FamiliaProduto {
    id: number
    codigo: string
    nome: string
    descricao?: string
    familiaPai?: Pick<FamiliaProduto, 'id' | 'nome'>
    subfamilias?: FamiliaProduto[]
}

export interface FamiliaProdutoPayload {
    codigo: string
    nome: string
    descricao?: string
    familiaPai?: { id: number }
}

// ─── Posto de Trabalho ─────────────────────────────────────────────────────────
export type StatusPosto = 'ATIVO' | 'INATIVO'

export interface PostoTrabalho {
    id: number
    codigo: string
    nome: string
    setor?: string
    custoHora: number
    custoFixoHora?: number
    status: StatusPosto
}

export interface PostoTrabalhoPayload {
    codigo: string
    nome: string
    setor?: string
    custoHora: number
    custoFixoHora?: number
    status: StatusPosto
}