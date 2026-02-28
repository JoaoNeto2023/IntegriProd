export const queryKeys = {

    // ─── Cadastros Básicos ───────────────────────────────────────────────────────
    tiposProduto: {
        all: ['tipos-produto'] as const,
        byId: (id: number) => ['tipos-produto', id] as const,
        byCodigo: (codigo: string) => ['tipos-produto', 'codigo', codigo] as const,
        byNatureza: (n: string) => ['tipos-produto', 'natureza', n] as const,
    },

    unidadesMedida: {
        all: ['unidades-medida'] as const,
        bySigla: (sigla: string) => ['unidades-medida', sigla] as const,
    },

    familiasProduto: {
        all: ['familias-produto'] as const,
        raizes: ['familias-produto', 'raizes'] as const,
        byId: (id: number) => ['familias-produto', id] as const,
        subfamilias: (id: number) => ['familias-produto', id, 'subfamilias'] as const,
    },

    postos: {
        all: ['postos-trabalho'] as const,
        ativos: ['postos-trabalho', 'ativos'] as const,
        byId: (id: number) => ['postos-trabalho', id] as const,
        bySetor: (setor: string) => ['postos-trabalho', 'setor', setor] as const,
    },

    // ─── Estrutura Organizacional ────────────────────────────────────────────────
    empresas: {
        all: ['empresas'] as const,
        ativas: ['empresas', 'ativas'] as const,
        byId: (id: number) => ['empresas', id] as const,
    },

    filiais: {
        all: ['filiais'] as const,
        ativas: ['filiais', 'ativas'] as const,
        byId: (id: number) => ['filiais', id] as const,
        byEmpresa: (id: number) => ['filiais', 'empresa', id] as const,
        byUf: (uf: string) => ['filiais', 'uf', uf] as const,
    },

    // ─── Pessoas ─────────────────────────────────────────────────────────────────
    pessoas: {
        all: ['pessoas'] as const,
        ativas: ['pessoas', 'ativas'] as const,
        byId: (id: number) => ['pessoas', id] as const,
        clientes: ['pessoas', 'clientes'] as const,
        fornecedores: ['pessoas', 'fornecedores'] as const,
        funcionarios: ['pessoas', 'funcionarios'] as const,
        enderecos: (id: number) => ['pessoas', id, 'enderecos'] as const,
        classificacoes: (id: number) => ['pessoas', id, 'classificacoes'] as const,
    },

    // ─── Produtos ────────────────────────────────────────────────────────────────
    produtos: {
        all: ['produtos'] as const,
        ativos: ['produtos', 'ativos'] as const,
        byId: (id: number) => ['produtos', id] as const,
        byTipo: (tipoId: number) => ['produtos', 'tipo', tipoId] as const,
        byFamilia: (famId: number) => ['produtos', 'familia', famId] as const,
    },

    estrutura: {
        byProduto: (produtoId: number, versao?: string) =>
            versao
                ? ['produtos-estrutura', 'produto', produtoId, versao] as const
                : ['produtos-estrutura', 'produto', produtoId] as const,
        versoes: (produtoId: number) =>
            ['produtos-estrutura', 'produto', produtoId, 'versoes'] as const,
        custo: (produtoId: number, versao?: string) =>
            ['produtos-estrutura', 'produto', produtoId, 'custo', versao] as const,
    },

    roteiro: {
        byProduto: (produtoId: number, versao?: string) =>
            versao
                ? ['roteiros-producao', 'produto', produtoId, versao] as const
                : ['roteiros-producao', 'produto', produtoId] as const,
        versoes: (produtoId: number) =>
            ['roteiros-producao', 'produto', produtoId, 'versoes'] as const,
        tempoTotal: (produtoId: number, versao?: string) =>
            ['roteiros-producao', 'produto', produtoId, 'tempo-total', versao] as const,
    },

    // ─── Estoque ─────────────────────────────────────────────────────────────────
    estoque: {
        all: ['estoque'] as const,
        byId: (id: number) => ['estoque', id] as const,
        byFilial: (id: number) => ['estoque', 'filial', id] as const,
        byProduto: (id: number) => ['estoque', 'produto', id] as const,
        saldo: (filialId: number, produtoId: number) =>
            ['estoque', 'saldo', filialId, produtoId] as const,
        disponivel: (filialId: number, produtoId: number) =>
            ['estoque', 'disponivel', filialId, produtoId] as const,
        alertasAbaixoMinimo: ['estoque', 'alertas', 'abaixo-minimo'] as const,
        alertasAVencer: ['estoque', 'alertas', 'a-vencer'] as const,
        alertasVencidos: ['estoque', 'alertas', 'vencidos'] as const,
    },

    movimentos: {
        all: ['movimentos-estoque'] as const,
        byId: (id: number) => ['movimentos-estoque', id] as const,
        byFilial: (id: number) => ['movimentos-estoque', 'filial', id] as const,
        byProduto: (id: number) => ['movimentos-estoque', 'produto', id] as const,
        byTipo: (tipo: string) => ['movimentos-estoque', 'tipo', tipo] as const,
        byPeriodo: (i: string, f: string) => ['movimentos-estoque', 'periodo', i, f] as const,
        byLote: (lote: string) => ['movimentos-estoque', 'lote', lote] as const,
    },

    // ─── Produção ────────────────────────────────────────────────────────────────
    ordens: {
        all: ['ordens-producao'] as const,
        byId: (id: number) => ['ordens-producao', id] as const,
        byStatus: (status: string) => ['ordens-producao', 'status', status] as const,
        byProduto: (id: number) => ['ordens-producao', 'produto', id] as const,
        byPeriodo: (i: string, f: string) => ['ordens-producao', 'periodo', i, f] as const,
        abertas: ['ordens-producao', 'abertas'] as const,
        atrasadas: ['ordens-producao', 'atrasadas'] as const,
    },

    apontamentos: {
        byOP: (ordemId: number) => ['apontamentos', 'ordem', ordemId] as const,
        byPosto: (postoId: number) => ['apontamentos', 'posto', postoId] as const,
        byData: (data: string) => ['apontamentos', 'data', data] as const,
        byPeriodo: (i: string, f: string) => ['apontamentos', 'periodo', i, f] as const,
    },

    consumos: {
        byOP: (ordemId: number) => ['consumos', 'ordem', ordemId] as const,
        byProduto: (produtoId: number) => ['consumos', 'produto', produtoId] as const,
    },

    qualidade: {
        byOP: (ordemId: number) => ['qualidade', 'ordem', ordemId] as const,
        byProduto: (produtoId: number) => ['qualidade', 'produto', produtoId] as const,
        byResultado: (resultado: string) => ['qualidade', 'resultado', resultado] as const,
        byLote: (lote: string) => ['qualidade', 'lote', lote] as const,
    },

}