package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.MovimentoEstoque;
import com.inovs.integrprod.model.entity.Estoque;
import com.inovs.integrprod.repository.MovimentoEstoqueRepository;
import com.inovs.integrprod.repository.EstoqueRepository;
import com.inovs.integrprod.service.MovimentoEstoqueService;
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
public class MovimentoEstoqueServiceImpl implements MovimentoEstoqueService {

    private final MovimentoEstoqueRepository movimentoRepository;
    private final EstoqueRepository estoqueRepository;

    @Override
    public MovimentoEstoque salvar(MovimentoEstoque movimento) {
        validarMovimento(movimento);
        return movimentoRepository.save(movimento);
    }

    @Override
    public MovimentoEstoque buscarPorId(Long id) {
        return movimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimento não encontrado com ID: " + id));
    }

    @Override
    public List<MovimentoEstoque> listarTodos() {
        return movimentoRepository.findAll();
    }

    @Override
    public List<MovimentoEstoque> buscarPorFilial(Long filialId) {
        return movimentoRepository.findByFilialId(filialId);
    }

    @Override
    public List<MovimentoEstoque> buscarPorProduto(Long produtoId) {
        return movimentoRepository.findByProdutoId(produtoId);
    }

    @Override
    public List<MovimentoEstoque> buscarPorTipo(String tipoMovimento) {
        return movimentoRepository.findByTipoMovimento(tipoMovimento);
    }

    @Override
    public List<MovimentoEstoque> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(23, 59, 59);
        return movimentoRepository.findByDataMovimentoBetween(inicioDateTime, fimDateTime);
    }

    @Override
    public List<MovimentoEstoque> buscarPorDocumento(String documentoTipo, String documentoNumero) {
        return movimentoRepository.findByDocumentoTipoAndDocumentoNumero(documentoTipo, documentoNumero);
    }

    @Override
    public List<MovimentoEstoque> buscarPorLote(String lote) {
        return movimentoRepository.findByLote(lote);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        MovimentoEstoque movimento = buscarPorId(id);

        // Verificar se pode deletar (movimento antigo, etc)
        if (movimento.getDataMovimento().isBefore(LocalDateTime.now().minusDays(30))) {
            throw new BusinessException("Não é possível deletar movimentos com mais de 30 dias");
        }

        movimentoRepository.delete(movimento);
    }

    @Override
    @Transactional
    public MovimentoEstoque estornarMovimento(Long id, String usuario) {
        MovimentoEstoque movimentoOriginal = buscarPorId(id);

        // Criar movimento de estorno
        MovimentoEstoque estorno = new MovimentoEstoque();
        estorno.setFilial(movimentoOriginal.getFilial());
        estorno.setProduto(movimentoOriginal.getProduto());
        estorno.setEstoque(movimentoOriginal.getEstoque());
        estorno.setTipoMovimento("ESTORNO");
        estorno.setDocumentoTipo("ESTORNO");
        estorno.setDocumentoNumero(movimentoOriginal.getId().toString());
        estorno.setQuantidade(movimentoOriginal.getQuantidade().negate());
        estorno.setValorUnitario(movimentoOriginal.getValorUnitario());
        estorno.setValorTotal(movimentoOriginal.getValorTotal().negate());
        estorno.setLoteOrigem(movimentoOriginal.getLoteDestino());
        estorno.setLoteDestino(movimentoOriginal.getLoteOrigem());
        estorno.setUsuarioMovimento(usuario);
        estorno.setObservacao("Estorno do movimento " + id);
        estorno.setMovimentoOrigem(movimentoOriginal);

        // Atualizar estoque
        Estoque estoque = movimentoOriginal.getEstoque();
        if (estoque != null) {
            BigDecimal novaQuantidade = estoque.getQuantidade().subtract(movimentoOriginal.getQuantidade());
            estoque.setQuantidade(novaQuantidade);
            estoqueRepository.save(estoque);
        }

        return movimentoRepository.save(estorno);
    }

    @Override
    public byte[] gerarRelatorioMovimentos(LocalDate inicio, LocalDate fim) {
        // Falta Implementar geração de relatório CSV/PDF
        // Por enquanto retorna array vazio
        return new byte[0];
    }

    @Override
    public void validarMovimento(MovimentoEstoque movimento) {
        if (movimento.getFilial() == null || movimento.getFilial().getId() == null) {
            throw new BusinessException("Filial é obrigatória");
        }

        if (movimento.getProduto() == null || movimento.getProduto().getId() == null) {
            throw new BusinessException("Produto é obrigatório");
        }

        if (movimento.getQuantidade() == null || movimento.getQuantidade().compareTo(java.math.BigDecimal.ZERO) == 0) {
            throw new BusinessException("Quantidade deve ser diferente de zero");
        }
    }
}