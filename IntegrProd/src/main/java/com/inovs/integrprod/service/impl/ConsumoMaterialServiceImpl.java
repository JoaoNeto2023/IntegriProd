package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.ConsumoMaterial;
import com.inovs.integrprod.repository.ConsumoMaterialRepository;
import com.inovs.integrprod.repository.OrdemProducaoRepository;
import com.inovs.integrprod.repository.ProdutoRepository;
import com.inovs.integrprod.repository.EstoqueRepository;
import com.inovs.integrprod.service.ConsumoMaterialService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConsumoMaterialServiceImpl implements ConsumoMaterialService {

    private final ConsumoMaterialRepository consumoRepository;
    private final OrdemProducaoRepository ordemRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    @Override
    public ConsumoMaterial salvar(ConsumoMaterial consumo) {
        validarConsumo(consumo);

        // Verificar se a ordem existe
        if (consumo.getOrdemProducao() != null && consumo.getOrdemProducao().getId() != null) {
            ordemRepository.findById(consumo.getOrdemProducao().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ordem de produção não encontrada"));
        }

        // Verificar se o produto existe
        if (consumo.getProduto() != null && consumo.getProduto().getId() != null) {
            produtoRepository.findById(consumo.getProduto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        }

        // Verificar se o estoque existe (se informado)
        if (consumo.getEstoque() != null && consumo.getEstoque().getId() != null) {
            estoqueRepository.findById(consumo.getEstoque().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Estoque não encontrado"));
        }

        return consumoRepository.save(consumo);
    }

    @Override
    public ConsumoMaterial buscarPorId(Long id) {
        return consumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumo não encontrado com ID: " + id));
    }

    @Override
    public List<ConsumoMaterial> listarTodos() {
        return consumoRepository.findAll();
    }

    @Override
    public List<ConsumoMaterial> buscarPorOrdem(Long ordemId) {
        return consumoRepository.findByOrdemProducaoId(ordemId);
    }

    @Override
    public List<ConsumoMaterial> buscarPorProduto(Long produtoId) {
        return consumoRepository.findByProdutoId(produtoId);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        ConsumoMaterial consumo = buscarPorId(id);
        consumoRepository.delete(consumo);
    }

    @Override
    @Transactional
    public ConsumoMaterial atualizar(Long id, ConsumoMaterial consumoAtualizado) {
        ConsumoMaterial consumoExistente = buscarPorId(id);

        consumoExistente.setQuantidadeConsumida(consumoAtualizado.getQuantidadeConsumida());
        consumoExistente.setQuantidadeDevolvida(consumoAtualizado.getQuantidadeDevolvida());
        consumoExistente.setObservacao(consumoAtualizado.getObservacao());

        return consumoRepository.save(consumoExistente);
    }

    @Override
    public void validarConsumo(ConsumoMaterial consumo) {
        if (consumo.getOrdemProducao() == null || consumo.getOrdemProducao().getId() == null) {
            throw new BusinessException("Ordem de produção é obrigatória");
        }

        if (consumo.getProduto() == null || consumo.getProduto().getId() == null) {
            throw new BusinessException("Produto é obrigatório");
        }

        if (consumo.getQuantidadeConsumida() == null ||
                consumo.getQuantidadeConsumida().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Quantidade consumida deve ser maior que zero");
        }
    }
}