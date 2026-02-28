import api from '@/lib/api'
import type {
    Estoque,
    EstoquePayload,
    MovimentoEstoque,
    EntradaPayload,
    SaidaPayload,
    TransferenciaPayload,
    AjustePayload,
    TipoMovimento,
} from '@/types/estoque'

export const estoqueService = {
    // ─── Estoque ─────────────────────────────────────────────────────────────────
    listar: () =>
        api.get<Estoque[]>('/estoque').then(r => r.data),

    buscarPorId: (id: number) =>
        api.get<Estoque>(`/estoque/${id}`).then(r => r.data),

    buscarPorFilial: (filialId: number) =>
        api.get<Estoque[]>(`/estoque/filial/${filialId}`).then(r => r.data),

    buscarPorProduto: (produtoId: number) =>
        api.get<Estoque[]>(`/estoque/produto/${produtoId}`).then(r => r.data),

    buscarPorFilialEProduto: (filialId: number, produtoId: number) =>
        api.get<Estoque[]>(`/estoque/filial/${filialId}/produto/${produtoId}`).then(r => r.data),

    buscarPorLote: (filialId: number, produtoId: number, lote: string) =>
        api.get<Estoque>('/estoque/lote', { params: { filialId, produtoId, lote } }).then(r => r.data),

    consultarSaldo: (filialId: number, produtoId: number) =>
        api.get<{ saldo: number }>('/estoque/saldo', { params: { filialId, produtoId } }).then(r => r.data),

    consultarDisponivel: (filialId: number, produtoId: number) =>
        api.get<{ disponivel: number }>('/estoque/disponivel', { params: { filialId, produtoId } }).then(r => r.data),

    alertasAbaixoMinimo: () =>
        api.get<Estoque[]>('/estoque/alertas/abaixo-minimo').then(r => r.data),

    alertasAVencer: (dias: number = 30) =>
        api.get<Estoque[]>('/estoque/alertas/a-vencer', { params: { dias } }).then(r => r.data),

    alertasVencidos: () =>
        api.get<Estoque[]>('/estoque/alertas/vencidos').then(r => r.data),

    criar: (payload: EstoquePayload) =>
        api.post<Estoque>('/estoque', payload).then(r => r.data),

    atualizar: (id: number, payload: Partial<EstoquePayload>) =>
        api.put<Estoque>(`/estoque/${id}`, payload).then(r => r.data),

    excluir: (id: number) =>
        api.delete(`/estoque/${id}`),

    // ─── Movimentos ──────────────────────────────────────────────────────────────
    entrada: (payload: EntradaPayload) =>
        api.post<MovimentoEstoque>('/estoque/movimentos/entrada', payload).then(r => r.data),

    saida: (payload: SaidaPayload) =>
        api.post<MovimentoEstoque>('/estoque/movimentos/saida', payload).then(r => r.data),

    transferencia: (payload: TransferenciaPayload) =>
        api.post<MovimentoEstoque>('/estoque/movimentos/transferencia', payload).then(r => r.data),

    ajuste: (payload: AjustePayload) =>
        api.post<MovimentoEstoque>('/estoque/movimentos/ajuste', payload).then(r => r.data),

    // ─── Histórico de Movimentos ─────────────────────────────────────────────────
    listarMovimentos: () =>
        api.get<MovimentoEstoque[]>('/movimentos-estoque').then(r => r.data),

    buscarMovimentoPorId: (id: number) =>
        api.get<MovimentoEstoque>(`/movimentos-estoque/${id}`).then(r => r.data),

    buscarMovimentosPorFilial: (filialId: number) =>
        api.get<MovimentoEstoque[]>(`/movimentos-estoque/filial/${filialId}`).then(r => r.data),

    buscarMovimentosPorProduto: (produtoId: number) =>
        api.get<MovimentoEstoque[]>(`/movimentos-estoque/produto/${produtoId}`).then(r => r.data),

    buscarMovimentosPorTipo: (tipo: TipoMovimento) =>
        api.get<MovimentoEstoque[]>(`/movimentos-estoque/tipo/${tipo}`).then(r => r.data),

    buscarMovimentosPorPeriodo: (inicio: string, fim: string) =>
        api.get<MovimentoEstoque[]>('/movimentos-estoque/periodo', { params: { inicio, fim } }).then(r => r.data),

    buscarMovimentosPorLote: (lote: string) =>
        api.get<MovimentoEstoque[]>(`/movimentos-estoque/lote/${lote}`).then(r => r.data),

    estornarMovimento: (id: number, usuario: string) =>
        api.post<MovimentoEstoque>(`/movimentos-estoque/${id}/estornar`, null, { params: { usuario } }).then(r => r.data),
}