import api from '@/lib/api'
import type {
    TipoProduto,
    TipoProdutoPayload,
    UnidadeMedida,
    UnidadeMedidaPayload,
    FamiliaProduto,
    FamiliaProdutoPayload,
} from '@/types/cadastros'

// ─── Tipo de Produto ───────────────────────────────────────────────────────────
export const tiposProdutoService = {
    listar: () =>
        api.get<TipoProduto[]>('/tipos-produto').then(r => r.data),

    buscarPorId: (id: number) =>
        api.get<TipoProduto>(`/tipos-produto/${id}`).then(r => r.data),

    buscarPorCodigo: (codigo: string) =>
        api.get<TipoProduto>(`/tipos-produto/codigo/${codigo}`).then(r => r.data),

    buscarPorNatureza: (natureza: string) =>
        api.get<TipoProduto[]>(`/tipos-produto/natureza/${natureza}`).then(r => r.data),

    criar: (payload: TipoProdutoPayload) =>
        api.post<TipoProduto>('/tipos-produto', payload).then(r => r.data),

    atualizar: (id: number, payload: TipoProdutoPayload) =>
        api.put<TipoProduto>(`/tipos-produto/${id}`, payload).then(r => r.data),

    excluir: (id: number) =>
        api.delete(`/tipos-produto/${id}`),
}

// ─── Unidade de Medida ─────────────────────────────────────────────────────────
export const unidadesMedidaService = {
    listar: () =>
        api.get<UnidadeMedida[]>('/unidades-medida').then(r => r.data),

    buscarPorSigla: (sigla: string) =>
        api.get<UnidadeMedida>(`/unidades-medida/${sigla}`).then(r => r.data),

    buscarPorDescricao: (descricao: string) =>
        api.get<UnidadeMedida[]>('/unidades-medida/buscar', { params: { descricao } }).then(r => r.data),

    criar: (payload: UnidadeMedidaPayload) =>
        api.post<UnidadeMedida>('/unidades-medida', payload).then(r => r.data),

    atualizar: (sigla: string, payload: UnidadeMedidaPayload) =>
        api.put<UnidadeMedida>(`/unidades-medida/${sigla}`, payload).then(r => r.data),

    excluir: (sigla: string) =>
        api.delete(`/unidades-medida/${sigla}`),
}

// ─── Família de Produto ────────────────────────────────────────────────────────
export const familiasProdutoService = {
    listar: () =>
        api.get<FamiliaProduto[]>('/familias-produto').then(r => r.data),

    listarRaizes: () =>
        api.get<FamiliaProduto[]>('/familias-produto/raizes').then(r => r.data),

    buscarPorId: (id: number) =>
        api.get<FamiliaProduto>(`/familias-produto/${id}`).then(r => r.data),

    buscarPorCodigo: (codigo: string) =>
        api.get<FamiliaProduto>(`/familias-produto/codigo/${codigo}`).then(r => r.data),

    buscarPorNome: (nome: string) =>
        api.get<FamiliaProduto[]>('/familias-produto/buscar', { params: { nome } }).then(r => r.data),

    listarSubfamilias: (id: number) =>
        api.get<FamiliaProduto[]>(`/familias-produto/${id}/subfamilias`).then(r => r.data),

    criar: (payload: FamiliaProdutoPayload) =>
        api.post<FamiliaProduto>('/familias-produto', payload).then(r => r.data),

    atualizar: (id: number, payload: FamiliaProdutoPayload) =>
        api.put<FamiliaProduto>(`/familias-produto/${id}`, payload).then(r => r.data),

    excluir: (id: number) =>
        api.delete(`/familias-produto/${id}`),
}