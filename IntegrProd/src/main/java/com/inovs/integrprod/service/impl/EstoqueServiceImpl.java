package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.*;
import com.inovs.integrprod.repository.*;
import com.inovs.integrprod.service.EstoqueService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EstoqueServiceImpl implements EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final MovimentoEstoqueRepository movimentoRepository;
    private final SaldoEstoqueRepository saldoRepository;
    private final FilialRepository filialRepository;
    private final ProdutoRepository produtoRepository;

    // ========== OPERAÇÕES BÁSICAS ==========

    @Override
    public Estoque salvar(Estoque estoque) {
        validarEstoque(estoque);

        // Verificar se já existe lote para o mesmo produto/filial
        if (estoque.getLote() != null && !estoque.getLote().isEmpty()) {
            estoqueRepository.findByFilialIdAndProdutoIdAndLote(
                    estoque.getFilial().getId(),
                    estoque.getProduto().getId(),
                    estoque.getLote()
            ).ifPresent(e -> {
                throw new BusinessException("Já existe um lote com este código para o produto nesta filial");
            });
        }

        if (estoque.getStatusLote() == null) {
            estoque.setStatusLote("ATIVO");
        }

        if (estoque.getQuantidade() == null) {
            estoque.setQuantidade(BigDecimal.ZERO);
        }

        if (estoque.getReservado() == null) {
            estoque.setReservado(BigDecimal.ZERO);
        }

        return estoqueRepository.save(estoque);
    }

    @Override
    public Estoque buscarPorId(Long id) {
        return estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado com ID: " + id));
    }

    @Override
    public List<Estoque> listarTodos() {
        return estoqueRepository.findAll();
    }

    @Override
    public List<Estoque> buscarPorFilial(Long filialId) {
        if (!filialRepository.existsById(filialId)) {
            throw new ResourceNotFoundException("Filial não encontrada");
        }
        return estoqueRepository.findByFilialId(filialId);
    }

    @Override
    public List<Estoque> buscarPorProduto(Long produtoId) {
        if (!produtoRepository.existsById(produtoId)) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }
        return estoqueRepository.findByProdutoId(produtoId);
    }

    @Override
    public List<Estoque> buscarPorFilialEProduto(Long filialId, Long produtoId) {
        return estoqueRepository.findByFilialIdAndProdutoId(filialId, produtoId);
    }

    @Override
    public Estoque buscarPorLote(Long filialId, Long produtoId, String lote) {
        return estoqueRepository.findByFilialIdAndProdutoIdAndLote(filialId, produtoId, lote)
                .orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado"));
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Estoque estoque = buscarPorId(id);

        // Verificar se há movimentações associadas
        List<MovimentoEstoque> movimentos = movimentoRepository.findByLote(estoque.getLote());
        if (!movimentos.isEmpty()) {
            throw new BusinessException("Não é possível excluir estoque com movimentações associadas");
        }

        estoqueRepository.delete(estoque);
    }

    @Override
    @Transactional
    public Estoque atualizar(Long id, Estoque estoqueAtualizado) {
        Estoque estoqueExistente = buscarPorId(id);

        estoqueExistente.setLote(estoqueAtualizado.getLote());
        estoqueExistente.setDataFabricacao(estoqueAtualizado.getDataFabricacao());
        estoqueExistente.setDataValidade(estoqueAtualizado.getDataValidade());
        estoqueExistente.setLocalizacao(estoqueAtualizado.getLocalizacao());
        estoqueExistente.setCustoUnitario(estoqueAtualizado.getCustoUnitario());
        estoqueExistente.setStatusLote(estoqueAtualizado.getStatusLote());
        estoqueExistente.setDocumentoEntrada(estoqueAtualizado.getDocumentoEntrada());

        return estoqueRepository.save(estoqueExistente);
    }

    // ========== MOVIMENTAÇÕES ==========

    @Override
    @Transactional
    public MovimentoEstoque entradaEstoque(MovimentoEstoque movimento) {
        validarMovimento(movimento);
        movimento.setTipoMovimento("ENTRADA");

        // Buscar ou criar estoque
        Estoque estoque = buscarOuCriarEstoque(
                movimento.getFilial(),
                movimento.getProduto(),
                movimento.getLoteDestino()
        );

        // Atualizar quantidade
        BigDecimal novaQuantidade = estoque.getQuantidade().add(movimento.getQuantidade());
        estoque.setQuantidade(novaQuantidade);

        // Atualizar custo médio se tiver valor
        if (movimento.getValorUnitario() != null) {
            atualizarCustoMedio(estoque, movimento);
        }

        estoqueRepository.save(estoque);

        // Vincular movimento ao estoque
        movimento.setEstoque(estoque);
        movimento.setLoteOrigem(null);
        movimento.setLoteDestino(estoque.getLote());

        MovimentoEstoque movimentoSalvo = movimentoRepository.save(movimento);

        // Atualizar saldo do dia
        atualizarSaldoDiario(movimento.getFilial().getId(), movimento.getProduto().getId(), LocalDate.now());

        return movimentoSalvo;
    }

    @Override
    @Transactional
    public MovimentoEstoque saidaEstoque(MovimentoEstoque movimento) {
        validarMovimento(movimento);
        movimento.setTipoMovimento("SAIDA");

        // Buscar estoque
        Estoque estoque = estoqueRepository.findByFilialIdAndProdutoIdAndLote(
                movimento.getFilial().getId(),
                movimento.getProduto().getId(),
                movimento.getLoteOrigem()
        ).orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado para saída"));

        // Verificar saldo disponível
        BigDecimal disponivel = estoque.getDisponivel();
        if (disponivel.compareTo(movimento.getQuantidade()) < 0) {
            throw new BusinessException("Saldo insuficiente. Disponível: " + disponivel);
        }

        // Atualizar quantidade
        BigDecimal novaQuantidade = estoque.getQuantidade().subtract(movimento.getQuantidade());
        estoque.setQuantidade(novaQuantidade);

        estoqueRepository.save(estoque);

        // Vincular movimento ao estoque
        movimento.setEstoque(estoque);
        movimento.setLoteDestino(null);
        movimento.setValorUnitario(estoque.getCustoUnitario());
        movimento.setValorTotal(movimento.getQuantidade().multiply(estoque.getCustoUnitario()));

        MovimentoEstoque movimentoSalvo = movimentoRepository.save(movimento);

        // Atualizar saldo do dia
        atualizarSaldoDiario(movimento.getFilial().getId(), movimento.getProduto().getId(), LocalDate.now());

        return movimentoSalvo;
    }

    @Override
    @Transactional
    public MovimentoEstoque transferenciaEstoque(MovimentoEstoque movimento) {
        validarMovimento(movimento);
        movimento.setTipoMovimento("TRANSFERENCIA");

        // Buscar estoque origem
        Estoque estoqueOrigem = estoqueRepository.findByFilialIdAndProdutoIdAndLote(
                movimento.getFilial().getId(),
                movimento.getProduto().getId(),
                movimento.getLoteOrigem()
        ).orElseThrow(() -> new ResourceNotFoundException("Lote de origem não encontrado"));

        // Verificar saldo
        BigDecimal disponivel = estoqueOrigem.getDisponivel();
        if (disponivel.compareTo(movimento.getQuantidade()) < 0) {
            throw new BusinessException("Saldo insuficiente para transferência");
        }

        // Buscar ou criar estoque destino (na mesma filial ou em outra?)
        Estoque estoqueDestino = buscarOuCriarEstoque(
                movimento.getFilial(), // Simplificando: mesma filial
                movimento.getProduto(),
                movimento.getLoteDestino()
        );

        // Baixar do origem
        estoqueOrigem.setQuantidade(estoqueOrigem.getQuantidade().subtract(movimento.getQuantidade()));
        estoqueRepository.save(estoqueOrigem);

        // Adicionar no destino
        estoqueDestino.setQuantidade(estoqueDestino.getQuantidade().add(movimento.getQuantidade()));
        if (estoqueDestino.getCustoUnitario() == null) {
            estoqueDestino.setCustoUnitario(estoqueOrigem.getCustoUnitario());
        }
        estoqueRepository.save(estoqueDestino);

        // Registrar movimento
        movimento.setEstoque(estoqueOrigem);
        movimento.setValorUnitario(estoqueOrigem.getCustoUnitario());
        movimento.setValorTotal(movimento.getQuantidade().multiply(estoqueOrigem.getCustoUnitario()));

        return movimentoRepository.save(movimento);
    }

    @Override
    @Transactional
    public MovimentoEstoque ajusteEstoque(MovimentoEstoque movimento) {
        validarMovimento(movimento);
        movimento.setTipoMovimento("AJUSTE");

        Estoque estoque = estoqueRepository.findByFilialIdAndProdutoIdAndLote(
                movimento.getFilial().getId(),
                movimento.getProduto().getId(),
                movimento.getLoteOrigem()
        ).orElseThrow(() -> new ResourceNotFoundException("Lote não encontrado para ajuste"));

        BigDecimal diferenca = movimento.getQuantidade().subtract(estoque.getQuantidade());
        movimento.setQuantidade(diferenca.abs());
        movimento.setObservacao("Ajuste de estoque: " + movimento.getObservacao());

        estoque.setQuantidade(movimento.getQuantidade());
        estoqueRepository.save(estoque);

        movimento.setEstoque(estoque);
        return movimentoRepository.save(movimento);
    }

    // ========== CONSULTAS ==========

    @Override
    public BigDecimal consultarSaldo(Long filialId, Long produtoId) {
        BigDecimal saldo = estoqueRepository.sumQuantidadeByFilialAndProduto(filialId, produtoId);
        return saldo != null ? saldo : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal consultarSaldoDisponivel(Long filialId, Long produtoId) {
        List<Estoque> estoques = estoqueRepository.findByFilialIdAndProdutoId(filialId, produtoId);
        return estoques.stream()
                .map(Estoque::getDisponivel)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Estoque> consultarEstoqueAbaixoMinimo() {
        return estoqueRepository.findEstoqueAbaixoMinimo();
    }

    @Override
    public List<Estoque> consultarProdutosAVencer(int dias) {
        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(dias);
        return estoqueRepository.findProdutosAVencer(hoje, limite);
    }

    @Override
    public List<Estoque> consultarProdutosVencidos() {
        return estoqueRepository.findProdutosVencidos(LocalDate.now());
    }

    // ========== RELATÓRIOS ==========

    @Override
    public List<MovimentoEstoque> consultarMovimentosPeriodo(Long filialId, LocalDate inicio, LocalDate fim) {
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(23, 59, 59);
        return movimentoRepository.findByDataMovimentoBetween(inicioDateTime, fimDateTime);
    }

    @Override
    public List<MovimentoEstoque> consultarMovimentosProduto(Long produtoId) {
        return movimentoRepository.findByProdutoId(produtoId);
    }

    @Override
    @Transactional
    public void calcularSaldoDiario(Long filialId, LocalDate data) {
        List<Estoque> estoques = estoqueRepository.findByFilialId(filialId);

        for (Estoque estoque : estoques) {
            // Buscar saldo do dia anterior
            LocalDate diaAnterior = data.minusDays(1);
            SaldoEstoque saldoAnterior = saldoRepository
                    .findByFilialIdAndProdutoIdAndDataSaldo(filialId, estoque.getProduto().getId(), diaAnterior)
                    .orElse(null);

            // Calcular movimentos do dia
            LocalDateTime inicio = data.atStartOfDay();
            LocalDateTime fim = data.atTime(23, 59, 59);
            List<MovimentoEstoque> movimentos = movimentoRepository.findByDataMovimentoBetween(inicio, fim);

            BigDecimal entradas = movimentos.stream()
                    .filter(m -> m.getTipoMovimento().contains("ENTRADA"))
                    .map(MovimentoEstoque::getQuantidade)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saidas = movimentos.stream()
                    .filter(m -> m.getTipoMovimento().contains("SAIDA"))
                    .map(MovimentoEstoque::getQuantidade)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal saldoInicial = saldoAnterior != null ? saldoAnterior.getSaldoFinal() : BigDecimal.ZERO;
            BigDecimal saldoFinal = saldoInicial.add(entradas).subtract(saidas);

            // Criar ou atualizar saldo do dia
            SaldoEstoque saldo = saldoRepository
                    .findByFilialIdAndProdutoIdAndDataSaldo(filialId, estoque.getProduto().getId(), data)
                    .orElse(new SaldoEstoque());

            saldo.setFilial(estoque.getFilial());
            saldo.setProduto(estoque.getProduto());
            saldo.setDataSaldo(data);
            saldo.setSaldoInicial(saldoInicial);
            saldo.setEntradas(entradas);
            saldo.setSaidas(saidas);
            saldo.setSaldoFinal(saldoFinal);
            saldo.setValorTotal(saldoFinal.multiply(estoque.getCustoUnitario() != null ?
                    estoque.getCustoUnitario() : BigDecimal.ZERO));

            saldoRepository.save(saldo);
        }
    }

    // ========== MÉTODOS AUXILIARES ==========

    private Estoque buscarOuCriarEstoque(Filial filial, Produto produto, String lote) {
        if (lote != null && !lote.isEmpty()) {
            return estoqueRepository.findByFilialIdAndProdutoIdAndLote(filial.getId(), produto.getId(), lote)
                    .orElseGet(() -> {
                        Estoque novoEstoque = new Estoque();
                        novoEstoque.setFilial(filial);
                        novoEstoque.setProduto(produto);
                        novoEstoque.setLote(lote);
                        novoEstoque.setQuantidade(BigDecimal.ZERO);
                        novoEstoque.setReservado(BigDecimal.ZERO);
                        novoEstoque.setStatusLote("ATIVO");
                        return estoqueRepository.save(novoEstoque);
                    });
        } else {
            // Sem lote, retorna o primeiro estoque ou cria um genérico
            return estoqueRepository.findByFilialIdAndProdutoId(filial.getId(), produto.getId())
                    .stream()
                    .findFirst()
                    .orElseGet(() -> {
                        Estoque novoEstoque = new Estoque();
                        novoEstoque.setFilial(filial);
                        novoEstoque.setProduto(produto);
                        novoEstoque.setQuantidade(BigDecimal.ZERO);
                        novoEstoque.setReservado(BigDecimal.ZERO);
                        novoEstoque.setStatusLote("ATIVO");
                        return estoqueRepository.save(novoEstoque);
                    });
        }
    }

    private void atualizarCustoMedio(Estoque estoque, MovimentoEstoque movimento) {
        BigDecimal quantidadeAtual = estoque.getQuantidade();
        BigDecimal valorTotalAtual = quantidadeAtual.multiply(
                estoque.getCustoUnitario() != null ? estoque.getCustoUnitario() : BigDecimal.ZERO
        );
        BigDecimal valorTotalNovo = movimento.getQuantidade().multiply(movimento.getValorUnitario());
        BigDecimal valorTotalFinal = valorTotalAtual.add(valorTotalNovo);
        BigDecimal quantidadeFinal = quantidadeAtual.add(movimento.getQuantidade());

        if (quantidadeFinal.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal novoCustoMedio = valorTotalFinal.divide(quantidadeFinal, 4, BigDecimal.ROUND_HALF_UP);
            estoque.setCustoUnitario(novoCustoMedio);
        }
    }

    private void atualizarSaldoDiario(Long filialId, Long produtoId, LocalDate data) {
        // Implementação simplificada - pode ser melhorada depois
        calcularSaldoDiario(filialId, data);
    }

    // ========== VALIDAÇÕES ==========

    @Override
    public void validarEstoque(Estoque estoque) {
        if (estoque.getFilial() == null || estoque.getFilial().getId() == null) {
            throw new BusinessException("Filial é obrigatória");
        }

        if (estoque.getProduto() == null || estoque.getProduto().getId() == null) {
            throw new BusinessException("Produto é obrigatório");
        }

        if (estoque.getDataValidade() != null && estoque.getDataFabricacao() != null) {
            if (estoque.getDataValidade().isBefore(estoque.getDataFabricacao())) {
                throw new BusinessException("Data de validade não pode ser anterior à data de fabricação");
            }
        }
    }

    @Override
    public void validarMovimento(MovimentoEstoque movimento) {
        if (movimento.getFilial() == null || movimento.getFilial().getId() == null) {
            throw new BusinessException("Filial é obrigatória");
        }

        if (movimento.getProduto() == null || movimento.getProduto().getId() == null) {
            throw new BusinessException("Produto é obrigatório");
        }

        if (movimento.getQuantidade() == null || movimento.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Quantidade deve ser maior que zero");
        }

        if (movimento.getTipoMovimento() == null) {
            throw new BusinessException("Tipo de movimento é obrigatório");
        }
    }
}