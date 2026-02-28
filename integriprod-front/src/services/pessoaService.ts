import api from '@/lib/api'
import type {
    Pessoa,
    PessoaPayload,
    Endereco,
    EnderecoPayload,
    Classificacao,
    ClassificacaoPayload,
    TipoPessoa,
    TipoClassificacao,
} from '@/types/pessoas'

export const pessoasService = {
    // ─── Pessoa ─────────────────────────────────────────────────────────────────
    listar: () =>
        api.get<Pessoa[]>('/pessoas').then(r => r.data),

    listarAtivas: () =>
        api.get<Pessoa[]>('/pessoas/ativas').then(r => r.data),

    buscarPorId: (id: number) =>
        api.get<Pessoa>(`/pessoas/${id}`).then(r => r.data),

    buscarPorCpfCnpj: (cpfCnpj: string) =>
        api.get<Pessoa>(`/pessoas/cpf-cnpj/${cpfCnpj}`).then(r => r.data),

    buscarPorNome: (nome: string) =>
        api.get<Pessoa[]>('/pessoas/buscar/nome', { params: { nome } }).then(r => r.data),

    buscarPorTipo: (tipo: TipoPessoa) =>
        api.get<Pessoa[]>(`/pessoas/tipo/${tipo}`).then(r => r.data),

    listarClientes: () =>
        api.get<Pessoa[]>('/pessoas/clientes').then(r => r.data),

    listarFornecedores: () =>
        api.get<Pessoa[]>('/pessoas/fornecedores').then(r => r.data),

    listarFuncionarios: () =>
        api.get<Pessoa[]>('/pessoas/funcionarios').then(r => r.data),

    criar: (payload: PessoaPayload) =>
        api.post<Pessoa>('/pessoas', payload).then(r => r.data),

    atualizar: (id: number, payload: PessoaPayload) =>
        api.put<Pessoa>(`/pessoas/${id}`, payload).then(r => r.data),

    excluir: (id: number) =>
        api.delete(`/pessoas/${id}`),

    // ─── Endereços ───────────────────────────────────────────────────────────────
    listarEnderecos: (pessoaId: number) =>
        api.get<Endereco[]>(`/pessoas/${pessoaId}/enderecos`).then(r => r.data),

    buscarEnderecoPrincipal: (pessoaId: number) =>
        api.get<Endereco>(`/pessoas/${pessoaId}/enderecos/principal`).then(r => r.data),

    adicionarEndereco: (pessoaId: number, payload: EnderecoPayload) =>
        api.post<Endereco>(`/pessoas/${pessoaId}/enderecos`, payload).then(r => r.data),

    atualizarEndereco: (enderecoId: number, payload: EnderecoPayload) =>
        api.put<Endereco>(`/pessoas/enderecos/${enderecoId}`, payload).then(r => r.data),

    removerEndereco: (enderecoId: number) =>
        api.delete(`/pessoas/enderecos/${enderecoId}`),

    // ─── Classificações ──────────────────────────────────────────────────────────
    listarClassificacoes: (pessoaId: number) =>
        api.get<Classificacao[]>(`/pessoas/${pessoaId}/classificacoes`).then(r => r.data),

    adicionarClassificacao: (pessoaId: number, payload: ClassificacaoPayload) =>
        api.post<Classificacao>(`/pessoas/${pessoaId}/classificacoes`, payload).then(r => r.data),

    atualizarClassificacao: (pessoaId: number, tipo: TipoClassificacao, payload: Partial<ClassificacaoPayload>) =>
        api.put<Classificacao>(`/pessoas/${pessoaId}/classificacoes/${tipo}`, payload).then(r => r.data),

    removerClassificacao: (pessoaId: number, tipo: TipoClassificacao) =>
        api.delete(`/pessoas/${pessoaId}/classificacoes/${tipo}`),
}