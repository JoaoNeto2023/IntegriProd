// ─── Pessoa ────────────────────────────────────────────────────────────────────
export type TipoPessoa = 'PF' | 'PJ'
export type SituacaoPessoa = 'ATIVO' | 'INATIVO'

export interface Pessoa {
    id: number
    tipoPessoa: TipoPessoa
    nomeRazao: string
    nomeFantasia?: string
    cpfCnpj: string
    rgIe?: string
    dataNascimento?: string
    email?: string
    telefone1?: string
    celular?: string
    uf?: string
    cidade?: string
    situacao: SituacaoPessoa
    enderecos?: Endereco[]
    classificacoes?: Classificacao[]
}

export interface PessoaPayload {
    tipoPessoa: TipoPessoa
    nomeRazao: string
    cpfCnpj: string
    rgIe?: string
    dataNascimento?: string
    email?: string
    celular?: string
    uf?: string
    cidade?: string
    situacao: SituacaoPessoa
}

// ─── Endereço ──────────────────────────────────────────────────────────────────
export type TipoEndereco = 'RESIDENCIAL' | 'COMERCIAL' | 'COBRANCA' | 'ENTREGA'

export interface Endereco {
    id: number
    tipoEndereco: TipoEndereco
    logradouro: string
    numero: string
    complemento?: string
    bairro: string
    cidade: string
    uf: string
    cep: string
    principal: boolean
}

export interface EnderecoPayload {
    tipoEndereco: TipoEndereco
    logradouro: string
    numero: string
    complemento?: string
    bairro: string
    cidade: string
    uf: string
    cep: string
    principal: boolean
}

// ─── Classificação ─────────────────────────────────────────────────────────────
export type TipoClassificacao = 'CLIENTE' | 'FORNECEDOR' | 'FUNCIONARIO'

export interface Classificacao {
    tipo: TipoClassificacao
    limiteCredito?: number
    diasPrazo?: number
}

export interface ClassificacaoPayload {
    tipo: TipoClassificacao
    limiteCredito?: number
    diasPrazo?: number
}