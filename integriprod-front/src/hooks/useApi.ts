import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { queryKeys } from '@/lib/queryKeys'
import { tiposProdutoService, unidadesMedidaService, familiasProdutoService } from '@/services/tiposProdutoService'
import { postosService } from '@/services/postosService'
import { empresasService, filiaisService } from '@/services/empresaService'
import { pessoasService } from '@/services/pessoaService'
import { produtosService } from '@/services/produtoService'
import { estoqueService } from '@/services/estoqueService'
import { producaoService } from '@/services/producaoService'
import toast from 'react-hot-toast'

import type { TipoProdutoPayload, UnidadeMedidaPayload, FamiliaProdutoPayload, PostoTrabalhoPayload, StatusPosto } from '@/types/cadastros'
import type { EmpresaPayload, FilialPayload } from '@/types/organizacao'
import type { PessoaPayload, EnderecoPayload, ClassificacaoPayload, TipoClassificacao, TipoPessoa } from '@/types/pessoas'
import type { ProdutoPayload, ProdutoEstruturaPayload, RoteiroProducaoPayload, StatusProduto, FichaTecnicaPayload } from '@/types/produtos'
import type { EstoquePayload, EntradaPayload, SaidaPayload, TransferenciaPayload, AjustePayload, TipoMovimento } from '@/types/estoque'
import type { OrdemProducaoPayload, ApontamentoPayload, ConsumoPayload, InspecaoPayload, StatusOP, ResultadoInspecao } from '@/types/producao'

// ═══════════════════════════════════════════════════════════════════════════════
// TIPOS DE PRODUTO
// ═══════════════════════════════════════════════════════════════════════════════
export const useTiposProduto = () =>
    useQuery({ queryKey: queryKeys.tiposProduto.all, queryFn: tiposProdutoService.listar })

export const useTipoProduto = (id: number) =>
    useQuery({ queryKey: queryKeys.tiposProduto.byId(id), queryFn: () => tiposProdutoService.buscarPorId(id), enabled: !!id })

export const useCriarTipoProduto = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: TipoProdutoPayload) => tiposProdutoService.criar(payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.tiposProduto.all }),
    })
}

export const useAtualizarTipoProduto = (id: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: TipoProdutoPayload) => tiposProdutoService.atualizar(id, payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.tiposProduto.all }),
    })
}

export const useExcluirTipoProduto = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => tiposProdutoService.excluir(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.tiposProduto.all }),
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// UNIDADES DE MEDIDA
// ═══════════════════════════════════════════════════════════════════════════════
export const useUnidadesMedida = () =>
    useQuery({ queryKey: queryKeys.unidadesMedida.all, queryFn: unidadesMedidaService.listar })

export const useCriarUnidadeMedida = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: UnidadeMedidaPayload) => unidadesMedidaService.criar(payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.unidadesMedida.all }),
    })
}

export const useAtualizarUnidadeMedida = (sigla: string) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: UnidadeMedidaPayload) => unidadesMedidaService.atualizar(sigla, payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.unidadesMedida.all }),
    })
}

export const useExcluirUnidadeMedida = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (sigla: string) => unidadesMedidaService.excluir(sigla),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.unidadesMedida.all }),
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// FAMÍLIAS DE PRODUTO
// ═══════════════════════════════════════════════════════════════════════════════
export const useFamiliasProduto = () =>
    useQuery({ queryKey: queryKeys.familiasProduto.all, queryFn: familiasProdutoService.listar })

export const useFamiliasProdutoRaizes = () =>
    useQuery({ queryKey: queryKeys.familiasProduto.raizes, queryFn: familiasProdutoService.listarRaizes })

export const useFamiliaProduto = (id: number) =>
    useQuery({ queryKey: queryKeys.familiasProduto.byId(id), queryFn: () => familiasProdutoService.buscarPorId(id), enabled: !!id })

export const useCriarFamiliaProduto = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: FamiliaProdutoPayload) => familiasProdutoService.criar(payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.familiasProduto.all }),
    })
}

export const useAtualizarFamiliaProduto = (id: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: FamiliaProdutoPayload) => familiasProdutoService.atualizar(id, payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.familiasProduto.all }),
    })
}

export const useExcluirFamiliaProduto = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => familiasProdutoService.excluir(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.familiasProduto.all }),
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// POSTOS DE TRABALHO
// ═══════════════════════════════════════════════════════════════════════════════
export const usePostos = () =>
    useQuery({ queryKey: queryKeys.postos.all, queryFn: postosService.listar })

export const usePostosAtivos = () =>
    useQuery({ queryKey: queryKeys.postos.ativos, queryFn: postosService.listarAtivos })

export const usePosto = (id: number) =>
    useQuery({ queryKey: queryKeys.postos.byId(id), queryFn: () => postosService.buscarPorId(id), enabled: !!id })

export const useCriarPosto = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: PostoTrabalhoPayload) => postosService.criar(payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.postos.all }),
    })
}

export const useAtualizarPosto = (id: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: PostoTrabalhoPayload) => postosService.atualizar(id, payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.postos.all }),
    })
}

export const useAtualizarCustoPosto = (id: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: ({ custoHora, custoFixoHora }: { custoHora: number; custoFixoHora: number }) =>
            postosService.atualizarCusto(id, custoHora, custoFixoHora),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.postos.all }),
    })
}

export const useExcluirPosto = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => postosService.excluir(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.postos.all }),
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// EMPRESAS
// ═══════════════════════════════════════════════════════════════════════════════
export const useEmpresas = () =>
    useQuery({ queryKey: queryKeys.empresas.all, queryFn: empresasService.listar })

export const useEmpresasAtivas = () =>
    useQuery({ queryKey: queryKeys.empresas.ativas, queryFn: empresasService.listarAtivas })

export const useEmpresa = (id: number) =>
    useQuery({ queryKey: queryKeys.empresas.byId(id), queryFn: () => empresasService.buscarPorId(id), enabled: !!id })

export const useCriarEmpresa = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: EmpresaPayload) => empresasService.criar(payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.empresas.all }),
    })
}

export const useAtualizarEmpresa = (id: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: EmpresaPayload) => empresasService.atualizar(id, payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.empresas.all }),
    })
}

export const useExcluirEmpresa = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => empresasService.excluir(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.empresas.all }),
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// FILIAIS
// ═══════════════════════════════════════════════════════════════════════════════
export const useFiliais = () =>
    useQuery({ queryKey: queryKeys.filiais.all, queryFn: filiaisService.listar })

export const useFiliaisAtivas = () =>
    useQuery({ queryKey: queryKeys.filiais.ativas, queryFn: filiaisService.listarAtivas })

export const useFilial = (id: number) =>
    useQuery({ queryKey: queryKeys.filiais.byId(id), queryFn: () => filiaisService.buscarPorId(id), enabled: !!id })

export const useFiliaisPorEmpresa = (empresaId: number) =>
    useQuery({ queryKey: queryKeys.filiais.byEmpresa(empresaId), queryFn: () => filiaisService.buscarPorEmpresa(empresaId), enabled: !!empresaId })

export const useCriarFilial = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: FilialPayload) => filiaisService.criar(payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.filiais.all }),
    })
}

export const useAtualizarFilial = (id: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: FilialPayload) => filiaisService.atualizar(id, payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.filiais.all }),
    })
}

export const useExcluirFilial = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => filiaisService.excluir(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.filiais.all }),
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// PESSOAS
// ═══════════════════════════════════════════════════════════════════════════════
export const usePessoas = () =>
    useQuery({ queryKey: queryKeys.pessoas.all, queryFn: pessoasService.listar })

export const usePessoasAtivas = () =>
    useQuery({ queryKey: queryKeys.pessoas.ativas, queryFn: pessoasService.listarAtivas })

export const usePessoa = (id: number) =>
    useQuery({ queryKey: queryKeys.pessoas.byId(id), queryFn: () => pessoasService.buscarPorId(id), enabled: !!id })

export const useClientes = () =>
    useQuery({ queryKey: queryKeys.pessoas.clientes, queryFn: pessoasService.listarClientes })

export const useFornecedores = () =>
    useQuery({ queryKey: queryKeys.pessoas.fornecedores, queryFn: pessoasService.listarFornecedores })

export const useFuncionarios = () =>
    useQuery({ queryKey: queryKeys.pessoas.funcionarios, queryFn: pessoasService.listarFuncionarios })

export const useEnderecosPessoa = (pessoaId: number) =>
    useQuery({ queryKey: queryKeys.pessoas.enderecos(pessoaId), queryFn: () => pessoasService.listarEnderecos(pessoaId), enabled: !!pessoaId })

export const useClassificacoesPessoa = (pessoaId: number) =>
    useQuery({ queryKey: queryKeys.pessoas.classificacoes(pessoaId), queryFn: () => pessoasService.listarClassificacoes(pessoaId), enabled: !!pessoaId })

export const useCriarPessoa = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: PessoaPayload) => pessoasService.criar(payload),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.pessoas.all })
            toast.success('Pessoa criada com sucesso!')
        },
        onError: (error: any) => {
            toast.error(error.response?.data?.message || 'Erro ao criar pessoa')
        }
    })
}

export const useAtualizarPessoa = (id: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: PessoaPayload) => pessoasService.atualizar(id, payload),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.pessoas.all })
            qc.invalidateQueries({ queryKey: queryKeys.pessoas.byId(id) })
        },
    })
}

export const useExcluirPessoa = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => pessoasService.excluir(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.pessoas.all }),
    })
}

export const useAdicionarEndereco = (pessoaId: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: EnderecoPayload) => pessoasService.adicionarEndereco(pessoaId, payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.pessoas.enderecos(pessoaId) }),
    })
}

export const useAdicionarClassificacao = (pessoaId: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: ClassificacaoPayload) => pessoasService.adicionarClassificacao(pessoaId, payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.pessoas.classificacoes(pessoaId) }),
    })
}
// ═══════════════════════════════════════════════════════════════════════════════
// PRODUTOS
// ═══════════════════════════════════════════════════════════════════════════════
export const useProdutos = () =>
    useQuery({
        queryKey: queryKeys.produtos.all,
        queryFn: produtosService.listar
    })
export const useCriarProduto = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: ProdutoPayload) => produtosService.criar(payload),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.produtos.all })
            toast.success('Produto criado com sucesso!')
        },
        onError: (error: any) => {
            toast.error(error.response?.data?.message || 'Erro ao criar produto')
        }
    })
}

export const useExcluirProduto = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => produtosService.excluir(id),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.produtos.all })
            toast.success('Produto excluído com sucesso!')
        },
        onError: (error: any) => {
            toast.error(error.response?.data?.message || 'Erro ao excluir produto')
        }
    })

}

export const useAtualizarProduto = (id: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: ProdutoPayload) => produtosService.atualizar(id, payload),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.produtos.all })
            qc.invalidateQueries({ queryKey: queryKeys.produtos.byId(id) })
            toast.success('Produto atualizado com sucesso!')
        },
        onError: (error: any) => {
            toast.error(error.response?.data?.message || 'Erro ao atualizar produto')
        }
    })
}

export const useProduto = (id: number) =>
    useQuery({
        queryKey: queryKeys.produtos.byId(id),
        queryFn: () => produtosService.buscarPorId(id),
        enabled: !!id
    })

export const useEstruturaProduto = (produtoId: number, versao?: string) =>
    useQuery({
        queryKey: queryKeys.estrutura.byProduto(produtoId, versao),
        queryFn: () => produtosService.listarEstrutura(produtoId, versao),
        enabled: !!produtoId
    })

export const useVersoesEstrutura = (produtoId: number) =>
    useQuery({
        queryKey: queryKeys.estrutura.versoes(produtoId),
        queryFn: () => produtosService.listarVersoesEstrutura(produtoId),
        enabled: !!produtoId
    })

export const useCustoEstrutura = (produtoId: number, versao?: string) =>
    useQuery({
        queryKey: queryKeys.estrutura.custo(produtoId, versao),
        queryFn: () => produtosService.calcularCustoEstrutura(produtoId, versao),
        enabled: !!produtoId
    })

export const useRoteiroProduto = (produtoId: number, versao?: string) =>
    useQuery({
        queryKey: queryKeys.roteiro.byProduto(produtoId, versao),
        queryFn: () => produtosService.listarRoteiro(produtoId, versao),
        enabled: !!produtoId
    })

export const useVersoesRoteiro = (produtoId: number) =>
    useQuery({
        queryKey: queryKeys.roteiro.versoes(produtoId),
        queryFn: () => produtosService.listarVersoesRoteiro(produtoId),
        enabled: !!produtoId
    })

export const useTempoTotalRoteiro = (produtoId: number, versao?: string) =>
    useQuery({
        queryKey: queryKeys.roteiro.tempoTotal(produtoId, versao),
        queryFn: () => produtosService.calcularTempoTotalRoteiro(produtoId, versao),
        enabled: !!produtoId
    })

export const useExcluirProdutoPermanente = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => produtosService.excluirPermanente(id),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.produtos.all })
            toast.success('Produto excluído permanentemente!')
        },
        onError: (error: any) => {
            toast.error(error.response?.data?.message || 'Erro ao excluir produto permanentemente')
        }
    })
}


// ═══════════════════════════════════════════════════════════════════════════════
// ESTOQUE
// ═══════════════════════════════════════════════════════════════════════════════
export const useEstoque = () =>
    useQuery({ queryKey: queryKeys.estoque.all, queryFn: estoqueService.listar })

export const useEstoquePorFilial = (filialId: number) =>
    useQuery({ queryKey: queryKeys.estoque.byFilial(filialId), queryFn: () => estoqueService.buscarPorFilial(filialId), enabled: !!filialId })

export const useEstoquePorProduto = (produtoId: number) =>
    useQuery({ queryKey: queryKeys.estoque.byProduto(produtoId), queryFn: () => estoqueService.buscarPorProduto(produtoId), enabled: !!produtoId })

export const useSaldoEstoque = (filialId: number, produtoId: number) =>
    useQuery({ queryKey: queryKeys.estoque.saldo(filialId, produtoId), queryFn: () => estoqueService.consultarSaldo(filialId, produtoId), enabled: !!filialId && !!produtoId })

export const useAlertasAbaixoMinimo = () =>
    useQuery({ queryKey: queryKeys.estoque.alertasAbaixoMinimo, queryFn: estoqueService.alertasAbaixoMinimo })

export const useAlertasAVencer = (dias?: number) =>
    useQuery({ queryKey: queryKeys.estoque.alertasAVencer, queryFn: () => estoqueService.alertasAVencer(dias) })

export const useAlertasVencidos = () =>
    useQuery({ queryKey: queryKeys.estoque.alertasVencidos, queryFn: estoqueService.alertasVencidos })

export const useEntradaEstoque = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: EntradaPayload) => estoqueService.entrada(payload),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.estoque.all })
            qc.invalidateQueries({ queryKey: queryKeys.movimentos.all })
        },
    })
}

export const useSaidaEstoque = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: SaidaPayload) => estoqueService.saida(payload),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.estoque.all })
            qc.invalidateQueries({ queryKey: queryKeys.movimentos.all })
        },
    })
}

export const useTransferenciaEstoque = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: TransferenciaPayload) => estoqueService.transferencia(payload),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.estoque.all })
            qc.invalidateQueries({ queryKey: queryKeys.movimentos.all })
        },
    })
}

export const useAjusteEstoque = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: AjustePayload) => estoqueService.ajuste(payload),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.estoque.all })
            qc.invalidateQueries({ queryKey: queryKeys.movimentos.all })
        },
    })
}

export const useMovimentos = () =>
    useQuery({ queryKey: queryKeys.movimentos.all, queryFn: estoqueService.listarMovimentos })

export const useMovimentosPorPeriodo = (inicio: string, fim: string) =>
    useQuery({ queryKey: queryKeys.movimentos.byPeriodo(inicio, fim), queryFn: () => estoqueService.buscarMovimentosPorPeriodo(inicio, fim), enabled: !!inicio && !!fim })

export const useEstornarMovimento = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: ({ id, usuario }: { id: number; usuario: string }) => estoqueService.estornarMovimento(id, usuario),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.estoque.all })
            qc.invalidateQueries({ queryKey: queryKeys.movimentos.all })
        },
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// ORDENS DE PRODUÇÃO
// ═══════════════════════════════════════════════════════════════════════════════
export const useOrdens = () =>
    useQuery({ queryKey: queryKeys.ordens.all, queryFn: producaoService.listar })

export const useOrdem = (id: number) =>
    useQuery({ queryKey: queryKeys.ordens.byId(id), queryFn: () => producaoService.buscarPorId(id), enabled: !!id })

export const useOrdensAbertas = () =>
    useQuery({ queryKey: queryKeys.ordens.abertas, queryFn: producaoService.buscarAbertas })

export const useOrdensAtrasadas = () =>
    useQuery({ queryKey: queryKeys.ordens.atrasadas, queryFn: producaoService.buscarAtrasadas })

export const useOrdensPorStatus = (status: StatusOP) =>
    useQuery({ queryKey: queryKeys.ordens.byStatus(status), queryFn: () => producaoService.buscarPorStatus(status), enabled: !!status })

export const useCriarOrdem = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: OrdemProducaoPayload) => producaoService.criar(payload),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.ordens.all }),
    })
}

export const useAtualizarOrdem = (id: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: Partial<OrdemProducaoPayload>) => producaoService.atualizar(id, payload),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: queryKeys.ordens.all })
            qc.invalidateQueries({ queryKey: queryKeys.ordens.byId(id) })
        },
    })
}

export const useIniciarOrdem = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => producaoService.iniciar(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.ordens.all }),
    })
}

export const usePausarOrdem = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => producaoService.pausar(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.ordens.all }),
    })
}

export const useConcluirOrdem = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => producaoService.concluir(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.ordens.all }),
    })
}

export const useCancelarOrdem = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => producaoService.cancelar(id),
        onSuccess: () => qc.invalidateQueries({ queryKey: queryKeys.ordens.all }),
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// APONTAMENTOS
// ═══════════════════════════════════════════════════════════════════════════════
export const useApontamentosPorOP = (ordemId: number) =>
    useQuery({ queryKey: queryKeys.apontamentos.byOP(ordemId), queryFn: () => producaoService.listarApontamentosPorOP(ordemId), enabled: !!ordemId })

export const useCriarApontamento = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: ApontamentoPayload) => producaoService.criarApontamento(payload),
        onSuccess: (_, vars) => {
            qc.invalidateQueries({ queryKey: queryKeys.apontamentos.byOP(vars.ordemProducao.id) })
            qc.invalidateQueries({ queryKey: queryKeys.ordens.byId(vars.ordemProducao.id) })
        },
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// CONSUMOS
// ═══════════════════════════════════════════════════════════════════════════════
export const useConsumosPorOP = (ordemId: number) =>
    useQuery({ queryKey: queryKeys.consumos.byOP(ordemId), queryFn: () => producaoService.listarConsumosPorOP(ordemId), enabled: !!ordemId })

export const useRegistrarConsumo = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: ConsumoPayload) => producaoService.registrarConsumo(payload),
        onSuccess: (_, vars) => {
            qc.invalidateQueries({ queryKey: queryKeys.consumos.byOP(vars.ordemProducao.id) })
            qc.invalidateQueries({ queryKey: queryKeys.estoque.all })
        },
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// QUALIDADE
// ═══════════════════════════════════════════════════════════════════════════════
export const useInspecoesPorOP = (ordemId: number) =>
    useQuery({ queryKey: queryKeys.qualidade.byOP(ordemId), queryFn: () => producaoService.listarInspecoesPorOP(ordemId), enabled: !!ordemId })

export const useRegistrarInspecao = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: InspecaoPayload) => producaoService.registrarInspecao(payload),
        onSuccess: (_, vars) => qc.invalidateQueries({ queryKey: queryKeys.qualidade.byOP(vars.ordemProducao.id) }),
    })
}

// ═══════════════════════════════════════════════════════════════════════════════
// FICHA TÉCNICA
// ═══════════════════════════════════════════════════════════════════════════════
export const useFichaTecnica = (produtoId: number, versao?: string) =>
    useQuery({
        queryKey: ['fichaTecnica', produtoId, versao],
        queryFn: () => produtosService.listarEstrutura(produtoId, versao),
        enabled: !!produtoId
    })

export const useVersoesFichaTecnica = (produtoId: number) =>
    useQuery({
        queryKey: ['fichaTecnica', produtoId, 'versoes'],
        queryFn: () => produtosService.listarVersoesEstrutura(produtoId),
        enabled: !!produtoId
    })

export const useCriarItemFicha = () => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (payload: FichaTecnicaPayload) => {
            // Converte para o formato esperado
            const payloadConvertido: ProdutoEstruturaPayload = {
                ...payload,
                tipoComponente: payload.tipoComponente === 'MP' ? 'MP' :
                    payload.tipoComponente === 'EMBALAGEM' ? 'ME' :
                        payload.tipoComponente === 'INSUMO' ? 'PI' : 'MP'
            }
            return produtosService.criarEstrutura(payloadConvertido)
        },
        onSuccess: (_, vars) => {
            qc.invalidateQueries({ queryKey: ['fichaTecnica', vars.produtoPai.id] })
            toast.success('Item adicionado à ficha técnica!')
        },
        onError: (error: any) => {
            toast.error(error.response?.data?.message || 'Erro ao adicionar item')
        }
    })
}

export const useExcluirItemFicha = (produtoId: number) => {
    const qc = useQueryClient()
    return useMutation({
        mutationFn: (id: number) => produtosService.excluirEstrutura(id),
        onSuccess: () => {
            qc.invalidateQueries({ queryKey: ['fichaTecnica', produtoId] })
            toast.success('Item removido da ficha técnica!')
        },
        onError: (error: any) => {
            toast.error(error.response?.data?.message || 'Erro ao remover item')
        }
    })
}

export const useCalcularCustoFicha = (produtoId: number, versao?: string) =>
    useQuery({
        queryKey: ['fichaTecnica', produtoId, 'custo', versao],
        queryFn: () => produtosService.calcularCustoEstrutura(produtoId, versao),
        enabled: !!produtoId
    })