import type { Produto } from './produtos'
import type { Filial } from './organizacao'
import type { PostoTrabalho } from './cadastros'
import type { Estoque } from './estoque'

// ─── Ordem de Produção ─────────────────────────────────────────────────────────
export type StatusOP =
    | 'PLANEJADA'
    | 'LIBERADA'
    | 'EM_PRODUCAO'
    | 'PAUSADA'
    | 'CONCLUIDA'
    | 'CANCELADA'
    | 'ATRASADA'

export interface OrdemProducao {
    id: number
    filial: Pick<Filial, 'id' | 'nome'>
    numeroOp: string
    produto: Pick<Produto, 'id' | 'nome' | 'codigo'>
    quantidadePlanejada: number
    quantidadeProduzida?: number
    dataEmissao: string
    dataInicioPrevista?: string
    dataFimPrevista?: string
    dataInicioReal?: string
    dataFimReal?: string
    prioridade: number
    status: StatusOP
    observacoes?: string
    usuarioCriacao: string
}

export interface OrdemProducaoPayload {
    filial: { id: number }
    numeroOp: string
    produto: { id: number }
    quantidadePlanejada: number
    dataEmissao: string
    dataInicioPrevista?: string
    dataFimPrevista?: string
    prioridade: number
    status: StatusOP
    usuarioCriacao: string
}

// ─── Apontamento ───────────────────────────────────────────────────────────────
export type TipoApontamento = 'PRODUCAO' | 'SETUP' | 'PARADA'

export interface Apontamento {
    id: number
    ordemProducao: Pick<OrdemProducao, 'id' | 'numeroOp'>
    postoTrabalho: Pick<PostoTrabalho, 'id' | 'nome'>
    dataApontamento: string
    horaInicio: string
    horaFim: string
    quantidadeProduzida: number
    tipoApontamento: TipoApontamento
    observacoes?: string
    usuarioRegistro: string
}

export interface ApontamentoPayload {
    ordemProducao: { id: number }
    postoTrabalho: { id: number }
    dataApontamento: string
    horaInicio: string
    horaFim: string
    quantidadeProduzida: number
    tipoApontamento: TipoApontamento
    observacoes?: string
    usuarioRegistro: string
}

// ─── Consumo de Material ───────────────────────────────────────────────────────
export interface ConsumoMaterial {
    id: number
    ordemProducao: { id: number; numeroOp: string }
    produto: { id: number; nome: string; codigo: string }
    estoque?: { id: number; lote: string }
    quantidadePlanejada?: number
    quantidadeConsumida: number
    quantidadeDevolvida?: number
    dataConsumo: string
    usuarioConsumo: string
    valorUnitario?: number
    valorTotal?: number
    observacao?: string  // ← SINGULAR
    movimentoEstoqueId?: number
}

export interface ConsumoPayload {
    ordemProducao: { id: number }
    produto: { id: number }
    estoque: { id: number }
    quantidadeConsumida: number
    usuarioConsumo: string
}

// ─── Controle de Qualidade ─────────────────────────────────────────────────────
export type ResultadoInspecao = 'APROVADO' | 'REPROVADO' | 'PENDENTE'

export interface InspecaoQualidade {
    id: number
    ordemProducao: Pick<OrdemProducao, 'id' | 'numeroOp'>
    produto: Pick<Produto, 'id' | 'nome'>
    lote?: string
    quantidadeInspecionada: number
    quantidadeAprovada: number
    quantidadeReprovada: number
    resultado: ResultadoInspecao
    observacoes?: string
}

export interface InspecaoPayload {
    ordemProducao: { id: number }
    produto: { id: number }
    lote?: string
    quantidadeInspecionada: number
    quantidadeAprovada: number
    quantidadeReprovada: number
    resultado: ResultadoInspecao
    observacoes?: string
}