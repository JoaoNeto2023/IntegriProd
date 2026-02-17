package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.TipoProduto;
import com.inovs.integrprod.repository.TipoProdutoRepository;
import com.inovs.integrprod.service.TipoProdutoService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class TipoProdutoServiceImpl implements TipoProdutoService {

    private final TipoProdutoRepository tipoProdutoRepository;

    @Override
    public TipoProduto salvar(TipoProduto tipoProduto) {
        if (tipoProdutoRepository.existsByCodigo(tipoProduto.getCodigo())) {
            throw new BusinessException("Já existe um tipo de produto com o código: " + tipoProduto.getCodigo());
        }
        return tipoProdutoRepository.save(tipoProduto);
    }

    @Override
    public TipoProduto buscarPorId(Long id) {
        return tipoProdutoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de produto não encontrado com ID: " + id));
    }

    @Override
    public TipoProduto buscarPorCodigo(String codigo) {
        return tipoProdutoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de produto não encontrado com código: " + codigo));
    }

    @Override
    public List<TipoProduto> listarTodos() {
        return tipoProdutoRepository.findAll();
    }

    @Override
    public List<TipoProduto> buscarPorNatureza(String natureza) {
        return tipoProdutoRepository.findByNatureza(natureza);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        TipoProduto tipoProduto = buscarPorId(id);
        tipoProdutoRepository.delete(tipoProduto);
    }

    @Override
    @Transactional
    public TipoProduto atualizar(Long id, TipoProduto tipoProdutoAtualizado) {
        TipoProduto tipoProdutoExistente = buscarPorId(id);

        if (!tipoProdutoExistente.getCodigo().equals(tipoProdutoAtualizado.getCodigo()) &&
                tipoProdutoRepository.existsByCodigo(tipoProdutoAtualizado.getCodigo())) {
            throw new BusinessException("Já existe um tipo de produto com o código: " + tipoProdutoAtualizado.getCodigo());
        }

        tipoProdutoExistente.setCodigo(tipoProdutoAtualizado.getCodigo());
        tipoProdutoExistente.setDescricao(tipoProdutoAtualizado.getDescricao());
        tipoProdutoExistente.setNatureza(tipoProdutoAtualizado.getNatureza());

        return tipoProdutoRepository.save(tipoProdutoExistente);
    }
}