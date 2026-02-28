import api from '@/lib/api'
import type { Empresa, EmpresaPayload, Filial, FilialPayload } from '@/types/organizacao'

// ─── Empresa ───────────────────────────────────────────────────────────────────
export const empresasService = {
    listar: () =>
        api.get<Empresa[]>('/empresas').then(r => r.data),

    listarAtivas: () =>
        api.get<Empresa[]>('/empresas/ativas').then(r => r.data),

    buscarPorId: (id: number) =>
        api.get<Empresa>(`/empresas/${id}`).then(r => r.data),

    buscarPorCnpj: (cnpj: string) =>
        api.get<Empresa>(`/empresas/cnpj/${cnpj}`).then(r => r.data),

    buscarPorRazaoSocial: (razaoSocial: string) =>
        api.get<Empresa[]>('/empresas/buscar/razao-social', { params: { razaoSocial } }).then(r => r.data),

    criar: (payload: EmpresaPayload) =>
        api.post<Empresa>('/empresas', payload).then(r => r.data),

    atualizar: (id: number, payload: EmpresaPayload) =>
        api.put<Empresa>(`/empresas/${id}`, payload).then(r => r.data),

    excluir: (id: number) =>
        api.delete(`/empresas/${id}`),
}

// ─── Filial ────────────────────────────────────────────────────────────────────
export const filiaisService = {
    listar: () =>
        api.get<Filial[]>('/filiais').then(r => r.data),

    listarAtivas: () =>
        api.get<Filial[]>('/filiais/ativas').then(r => r.data),

    buscarPorId: (id: number) =>
        api.get<Filial>(`/filiais/${id}`).then(r => r.data),

    buscarPorCodigo: (codigo: string) =>
        api.get<Filial>(`/filiais/codigo/${codigo}`).then(r => r.data),

    buscarPorEmpresa: (empresaId: number) =>
        api.get<Filial[]>(`/filiais/empresa/${empresaId}`).then(r => r.data),

    buscarPorUf: (uf: string) =>
        api.get<Filial[]>(`/filiais/uf/${uf}`).then(r => r.data),

    criar: (payload: FilialPayload) =>
        api.post<Filial>('/filiais', payload).then(r => r.data),

    atualizar: (id: number, payload: FilialPayload) =>
        api.put<Filial>(`/filiais/${id}`, payload).then(r => r.data),

    excluir: (id: number) =>
        api.delete(`/filiais/${id}`),
}