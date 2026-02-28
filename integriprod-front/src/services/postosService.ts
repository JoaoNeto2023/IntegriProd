import api from '@/lib/api'
import type { PostoTrabalho, PostoTrabalhoPayload, StatusPosto } from '@/types/cadastros'

export const postosService = {
    listar: () =>
        api.get<PostoTrabalho[]>('/postos-trabalho').then(r => r.data),

    listarAtivos: () =>
        api.get<PostoTrabalho[]>('/postos-trabalho/ativos').then(r => r.data),

    buscarPorId: (id: number) =>
        api.get<PostoTrabalho>(`/postos-trabalho/${id}`).then(r => r.data),

    buscarPorCodigo: (codigo: string) =>
        api.get<PostoTrabalho>(`/postos-trabalho/codigo/${codigo}`).then(r => r.data),

    buscarPorNome: (nome: string) =>
        api.get<PostoTrabalho[]>('/postos-trabalho/buscar/nome', { params: { nome } }).then(r => r.data),

    buscarPorSetor: (setor: string) =>
        api.get<PostoTrabalho[]>(`/postos-trabalho/setor/${setor}`).then(r => r.data),

    buscarPorStatus: (status: StatusPosto) =>
        api.get<PostoTrabalho[]>(`/postos-trabalho/status/${status}`).then(r => r.data),

    criar: (payload: PostoTrabalhoPayload) =>
        api.post<PostoTrabalho>('/postos-trabalho', payload).then(r => r.data),

    atualizar: (id: number, payload: PostoTrabalhoPayload) =>
        api.put<PostoTrabalho>(`/postos-trabalho/${id}`, payload).then(r => r.data),

    atualizarCusto: (id: number, custoHora: number, custoFixoHora: number) =>
        api.patch<PostoTrabalho>(`/postos-trabalho/${id}/custo`, null, {
            params: { custoHora, custoFixoHora },
        }).then(r => r.data),

    excluir: (id: number) =>
        api.delete(`/postos-trabalho/${id}`),
}