import api from '@/lib/api'
import type {
    OrdemProducao,
    OrdemProducaoPayload,
    Apontamento,
    ApontamentoPayload,
    ConsumoMaterial,
    ConsumoPayload,
    InspecaoQualidade,
    InspecaoPayload,
    StatusOP,
    ResultadoInspecao,
} from '@/types/producao'

export const producaoService = {
    // ─── Ordens de Produção ───────────────────────────────────────────────────────
    listar: () =>
        api.get<OrdemProducao[]>('/ordens-producao').then(r => r.data),

    buscarPorId: (id: number) =>
        api.get<OrdemProducao>(`/ordens-producao/${id}`).then(r => r.data),

    buscarPorStatus: (status: StatusOP) =>
        api.get<OrdemProducao[]>(`/ordens-producao/status/${status}`).then(r => r.data),

    buscarPorProduto: (produtoId: number) =>
        api.get<OrdemProducao[]>(`/ordens-producao/produto/${produtoId}`).then(r => r.data),

    buscarPorPeriodo: (inicio: string, fim: string) =>
        api.get<OrdemProducao[]>('/ordens-producao/periodo', { params: { inicio, fim } }).then(r => r.data),

    buscarAbertas: () =>
        api.get<OrdemProducao[]>('/ordens-producao/abertas').then(r => r.data),

    buscarAtrasadas: () =>
        api.get<OrdemProducao[]>('/ordens-producao/atrasadas').then(r => r.data),

    criar: (payload: OrdemProducaoPayload) =>
        api.post<OrdemProducao>('/ordens-producao', payload).then(r => r.data),

    atualizar: (id: number, payload: Partial<OrdemProducaoPayload>) =>
        api.put<OrdemProducao>(`/ordens-producao/${id}`, payload).then(r => r.data),

    iniciar: (id: number) =>
        api.patch<OrdemProducao>(`/ordens-producao/${id}/iniciar`).then(r => r.data),

    pausar: (id: number) =>
        api.patch<OrdemProducao>(`/ordens-producao/${id}/pausar`).then(r => r.data),

    concluir: (id: number) =>
        api.patch<OrdemProducao>(`/ordens-producao/${id}/concluir`).then(r => r.data),

    cancelar: (id: number) =>
        api.patch<OrdemProducao>(`/ordens-producao/${id}/cancelar`).then(r => r.data),

    excluir: (id: number) =>
        api.delete(`/ordens-producao/${id}`),

    // ─── Apontamentos ─────────────────────────────────────────────────────────────
    listarApontamentosPorOP: (ordemId: number) =>
        api.get<Apontamento[]>(`/apontamentos/ordem/${ordemId}`).then(r => r.data),

    listarApontamentosPorPosto: (postoId: number) =>
        api.get<Apontamento[]>(`/apontamentos/posto/${postoId}`).then(r => r.data),

    listarApontamentosPorData: (data: string) =>
        api.get<Apontamento[]>(`/apontamentos/data/${data}`).then(r => r.data),

    listarApontamentosPorPeriodo: (inicio: string, fim: string) =>
        api.get<Apontamento[]>('/apontamentos/periodo', { params: { inicio, fim } }).then(r => r.data),

    criarApontamento: (payload: ApontamentoPayload) =>
        api.post<Apontamento>('/apontamentos', payload).then(r => r.data),

    atualizarApontamento: (id: number, payload: Partial<ApontamentoPayload>) =>
        api.put<Apontamento>(`/apontamentos/${id}`, payload).then(r => r.data),

    excluirApontamento: (id: number) =>
        api.delete(`/apontamentos/${id}`),

    // ─── Consumo de Material ──────────────────────────────────────────────────────
    listarConsumosPorOP: (ordemId: number) =>
        api.get<ConsumoMaterial[]>(`/consumos/ordem/${ordemId}`).then(r => r.data),

    listarConsumosPorProduto: (produtoId: number) =>
        api.get<ConsumoMaterial[]>(`/consumos/produto/${produtoId}`).then(r => r.data),

    registrarConsumo: (payload: ConsumoPayload) =>
        api.post<ConsumoMaterial>('/consumos', payload).then(r => r.data),

    devolverMaterial: (consumoId: number) =>
        api.post<ConsumoMaterial>(`/consumos/${consumoId}/devolver`).then(r => r.data),

    // ─── Controle de Qualidade ────────────────────────────────────────────────────
    listarInspecoesPorOP: (ordemId: number) =>
        api.get<InspecaoQualidade[]>(`/qualidade/ordem/${ordemId}`).then(r => r.data),

    listarInspecoesPorProduto: (produtoId: number) =>
        api.get<InspecaoQualidade[]>(`/qualidade/produto/${produtoId}`).then(r => r.data),

    listarInspecoesPorResultado: (resultado: ResultadoInspecao) =>
        api.get<InspecaoQualidade[]>(`/qualidade/resultado/${resultado}`).then(r => r.data),

    listarInspecoesPorLote: (lote: string) =>
        api.get<InspecaoQualidade[]>(`/qualidade/lote/${lote}`).then(r => r.data),

    registrarInspecao: (payload: InspecaoPayload) =>
        api.post<InspecaoQualidade>('/qualidade', payload).then(r => r.data),

    atualizarInspecao: (id: number, payload: Partial<InspecaoPayload>) =>
        api.put<InspecaoQualidade>(`/qualidade/${id}`, payload).then(r => r.data),

    excluirInspecao: (id: number) =>
        api.delete(`/qualidade/${id}`),
}