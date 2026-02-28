import api from '@/lib/api'
import type {
    Produto,
    ProdutoPayload,
    ProdutoEstrutura,
    ProdutoEstruturaPayload,
    RoteiroProducao,
    RoteiroProducaoPayload,
    StatusProduto,
} from '@/types/produtos'

export const produtosService = {
    // ─── Produto ─────────────────────────────────────────────────────────────────
    listar: () =>
        api.get<Produto[]>('/produtos').then(r => r.data),

    listarAtivos: () =>
        api.get<Produto[]>('/produtos/ativos').then(r => r.data),

    buscarPorId: (id: number) =>
        api.get<Produto>(`/produtos/${id}`).then(r => r.data),

    buscarPorCodigo: (codigo: string) =>
        api.get<Produto>(`/produtos/codigo/${codigo}`).then(r => r.data),

    buscarPorNome: (nome: string) =>
        api.get<Produto[]>('/produtos/buscar/nome', { params: { nome } }).then(r => r.data),

    buscarPorTipo: (tipoId: number) =>
        api.get<Produto[]>(`/produtos/tipo/${tipoId}`).then(r => r.data),

    buscarPorFamilia: (familiaId: number) =>
        api.get<Produto[]>(`/produtos/familia/${familiaId}`).then(r => r.data),

    criar: (payload: ProdutoPayload) =>
        api.post<Produto>('/produtos', payload).then(r => r.data),

    atualizar: (id: number, payload: ProdutoPayload) =>
        api.put<Produto>(`/produtos/${id}`, payload).then(r => r.data),

    atualizarPreco: (id: number, preco: number) =>
        api.patch<Produto>(`/produtos/${id}/preco`, null, { params: { preco } }).then(r => r.data),

    atualizarStatus: (id: number, status: StatusProduto) =>
        api.patch<Produto>(`/produtos/${id}/status`, null, { params: { status } }).then(r => r.data),

    excluir: (id: number) =>
        api.delete(`/produtos/${id}`),

    // ─── Estrutura ───────────────────────────────────────────────────────────────
    listarEstrutura: (produtoId: number, versao?: string) =>
        api.get<ProdutoEstrutura[]>(`/produtos-estrutura/produto/${produtoId}`, {
            params: versao ? { versao } : undefined,
        }).then(r => r.data),

    listarVersoesEstrutura: (produtoId: number) =>
        api.get<string[]>(`/produtos-estrutura/produto/${produtoId}/versoes`).then(r => r.data),

    calcularCustoEstrutura: (produtoId: number, versao?: string) =>
        api.get<{ custo: number }>(`/produtos-estrutura/produto/${produtoId}/custo`, {
            params: versao ? { versao } : undefined,
        }).then(r => r.data),

    copiarVersaoEstrutura: (produtoId: number, versaoOrigem: string, versaoDestino: string, usuario: string) =>
        api.post(`/produtos-estrutura/produto/${produtoId}/copiar-versao`, null, {
            params: { versaoOrigem, versaoDestino, usuario },
        }).then(r => r.data),

    buscarEstruturaPorId: (id: number) =>
        api.get<ProdutoEstrutura>(`/produtos-estrutura/${id}`).then(r => r.data),

    criarEstrutura: (payload: ProdutoEstruturaPayload) =>
        api.post<ProdutoEstrutura>('/produtos-estrutura', payload).then(r => r.data),

    atualizarEstrutura: (id: number, payload: Partial<ProdutoEstruturaPayload>) =>
        api.put<ProdutoEstrutura>(`/produtos-estrutura/${id}`, payload).then(r => r.data),

    excluirEstrutura: (id: number) =>
        api.delete(`/produtos-estrutura/${id}`),

    // ─── Roteiro ─────────────────────────────────────────────────────────────────
    listarRoteiro: (produtoId: number, versao?: string) =>
        api.get<RoteiroProducao[]>(`/roteiros-producao/produto/${produtoId}`, {
            params: versao ? { versao } : undefined,
        }).then(r => r.data),

    listarVersoesRoteiro: (produtoId: number) =>
        api.get<string[]>(`/roteiros-producao/produto/${produtoId}/versoes`).then(r => r.data),

    calcularTempoTotalRoteiro: (produtoId: number, versao?: string) =>
        api.get<{ totalMinutos: number }>(`/roteiros-producao/produto/${produtoId}/tempo-total`, {
            params: versao ? { versao } : undefined,
        }).then(r => r.data),

    copiarVersaoRoteiro: (produtoId: number, versaoOrigem: string, versaoDestino: string) =>
        api.post(`/roteiros-producao/produto/${produtoId}/copiar-versao`, null, {
            params: { versaoOrigem, versaoDestino },
        }).then(r => r.data),

    criarRoteiro: (payload: RoteiroProducaoPayload) =>
        api.post<RoteiroProducao>('/roteiros-producao', payload).then(r => r.data),

    atualizarRoteiro: (id: number, payload: Partial<RoteiroProducaoPayload>) =>
        api.put<RoteiroProducao>(`/roteiros-producao/${id}`, payload).then(r => r.data),

    excluirRoteiro: (id: number) =>
        api.delete(`/roteiros-producao/${id}`),

    excluirPermanente: (id: number) =>
        api.delete(`/produtos/${id}/permanente`).then(r => r.data),
}