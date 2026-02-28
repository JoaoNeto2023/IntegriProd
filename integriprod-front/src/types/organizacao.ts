// ─── Empresa ───────────────────────────────────────────────────────────────────
export type StatusEmpresa = 'ATIVO' | 'INATIVO'
export type RegimeTributario = '1' | '2' | '3'

export interface Empresa {
    id: number
    razaoSocial: string
    nomeFantasia?: string
    cnpj: string
    regimeTributario: RegimeTributario
    status: StatusEmpresa
}

export interface EmpresaPayload {
    razaoSocial: string
    nomeFantasia?: string
    cnpj: string
    regimeTributario: RegimeTributario
    status: StatusEmpresa
}

// ─── Filial ────────────────────────────────────────────────────────────────────
export type StatusFilial = 'ATIVO' | 'INATIVO'

export interface Filial {
    id: number
    empresa: Pick<Empresa, 'id' | 'razaoSocial'>
    codigo: string
    nome: string
    cnpj?: string
    cidade?: string
    uf?: string
    status: StatusFilial
}

export interface FilialPayload {
    empresa: { id: number }
    codigo: string
    nome: string
    cnpj?: string
    cidade?: string
    uf?: string
    status: StatusFilial
}