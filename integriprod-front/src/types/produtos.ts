import type { TipoProduto, UnidadeMedida, FamiliaProduto } from './cadastros'

// ─── Produto ───────────────────────────────────────────────────────────────────
export type StatusProduto = 'ATIVO' | 'INATIVO'

export interface Produto {
    id: number
    codigo: string
    nome: string
    tipoProduto: Pick<TipoProduto, 'id' | 'descricao'>
    unidadeMedida: Pick<UnidadeMedida, 'sigla' | 'descricao'>
    familiaProduto?: Pick<FamiliaProduto, 'id' | 'nome'>
    precoVenda?: number
    custoUnitario?: number
    markupPercentual?: number
    status: StatusProduto
}

export interface ProdutoPayload {
    codigo: string
    nome: string
    tipoProduto: { id: number }
    unidadeMedida: { sigla: string }
    familiaProduto?: { id: number }
    precoVenda?: number
    custoUnitario?: number
    markupPercentual?: number
    status: StatusProduto
}

// ─── Estrutura do Produto ──────────────────────────────────────────────────────
export type TipoComponente = 'MP' | 'PI' | 'ME'

export interface ProdutoEstrutura {
    id: number
    produtoPai: Pick<Produto, 'id' | 'nome' | 'codigo'>
    produtoFilho: Pick<Produto, 'id' | 'nome' | 'codigo'>
    versao: string
    quantidade: number
    unidadeMedida: Pick<UnidadeMedida, 'sigla'>
    nivel: number
    tipoComponente: TipoComponente
    percPerda: number
    ordemProducao: number
    dataInicioVigencia: string
}

export interface ProdutoEstruturaPayload {
    produtoPai: { id: number }
    produtoFilho: { id: number }
    versao: string
    quantidade: number
    unidadeMedida: { sigla: string }
    nivel: number
    tipoComponente: TipoComponente
    percPerda: number
    ordemProducao: number
    dataInicioVigencia: string
}

// ─── Roteiro de Produção ───────────────────────────────────────────────────────
export interface RoteiroProducao {
    id: number
    produto: Pick<Produto, 'id' | 'nome'>
    versao: string
    postoTrabalho: { id: number; nome: string; codigo: string }
    ordem: number
    operacaoDescricao: string
    tempoSetupMinutos: number
    tempoOperacaoMinutos: number
    maoObraNecessaria: number
}

export interface RoteiroProducaoPayload {
    produto: { id: number }
    versao: string
    postoTrabalho: { id: number }
    ordem: number
    operacaoDescricao: string
    tempoSetupMinutos: number
    tempoOperacaoMinutos: number
    maoObraNecessaria: number
}

// ─── Ficha Técnica ───────────────────────────────────────────────────────
export interface FichaTecnicaItem {
    id: number
    produtoPai: { id: number; nome: string; codigo: string }
    produtoFilho: { id: number; nome: string; codigo: string }
    versao: string
    quantidade: number
    unidadeMedida: { sigla: string; descricao: string }
    nivel: number
    tipoComponente: 'MP' | 'EMBALAGEM' | 'INSUMO' | 'SUBPRODUTO'
    percPerda: number
    ordemProducao: number
    dataInicioVigencia: string
    dataFimVigencia?: string
}

export interface FichaTecnicaPayload {
    produtoPai: { id: number }
    produtoFilho: { id: number }
    versao: string
    quantidade: number
    unidadeMedida: { sigla: string }
    nivel: number
    tipoComponente: 'MP' | 'EMBALAGEM' | 'INSUMO' | 'SUBPRODUTO'
    percPerda: number
    ordemProducao: number
    dataInicioVigencia: string
}