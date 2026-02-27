package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.RoteiroProducao;
import com.inovs.integrprod.repository.RoteiroProducaoRepository;
import com.inovs.integrprod.repository.ProdutoRepository;
import com.inovs.integrprod.repository.PostoTrabalhoRepository;
import com.inovs.integrprod.service.RoteiroProducaoService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class RoteiroProducaoServiceImpl implements RoteiroProducaoService {

    private final RoteiroProducaoRepository roteiroRepository;
    private final ProdutoRepository produtoRepository;
    private final PostoTrabalhoRepository postoTrabalhoRepository;

    @Override
    public RoteiroProducao salvar(RoteiroProducao roteiro) {
        validarRoteiro(roteiro);

        // Verificar se já existe operação na mesma ordem
        List<RoteiroProducao> existentes = roteiroRepository
                .findByProdutoIdAndVersaoOrderByOrdemAsc(roteiro.getProduto().getId(), roteiro.getVersao());

        boolean ordemDuplicada = existentes.stream()
                .anyMatch(r -> r.getOrdem().equals(roteiro.getOrdem()));

        if (ordemDuplicada) {
            throw new BusinessException("Já existe uma operação com esta ordem para esta versão");
        }

        return roteiroRepository.save(roteiro);
    }

    @Override
    public RoteiroProducao buscarPorId(Long id) {
        return roteiroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Roteiro não encontrado com ID: " + id));
    }

    @Override
    public List<RoteiroProducao> listarPorProdutoEversao(Long produtoId, String versao) {
        return roteiroRepository.findByProdutoIdAndVersaoOrderByOrdemAsc(produtoId, versao);
    }

    @Override
    public List<String> listarVersoes(Long produtoId) {
        return roteiroRepository.findVersoesByProdutoId(produtoId);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!roteiroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Roteiro não encontrado");
        }
        roteiroRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RoteiroProducao atualizar(Long id, RoteiroProducao roteiroAtualizado) {
        RoteiroProducao roteiroExistente = buscarPorId(id);

        roteiroExistente.setPostoTrabalho(roteiroAtualizado.getPostoTrabalho());
        roteiroExistente.setOrdem(roteiroAtualizado.getOrdem());
        roteiroExistente.setOperacaoDescricao(roteiroAtualizado.getOperacaoDescricao());
        roteiroExistente.setTempoSetupMinutos(roteiroAtualizado.getTempoSetupMinutos());
        roteiroExistente.setTempoOperacaoMinutos(roteiroAtualizado.getTempoOperacaoMinutos());
        roteiroExistente.setTempoEsperaMinutos(roteiroAtualizado.getTempoEsperaMinutos());
        roteiroExistente.setMaoObraNecessaria(roteiroAtualizado.getMaoObraNecessaria());
        roteiroExistente.setFerramentas(roteiroAtualizado.getFerramentas());
        roteiroExistente.setInstrucoes(roteiroAtualizado.getInstrucoes());

        return roteiroRepository.save(roteiroExistente);
    }

    @Override
    @Transactional
    public void copiarVersao(Long produtoId, String versaoOrigem, String versaoDestino) {
        List<RoteiroProducao> roteiroOrigem = roteiroRepository
                .findByProdutoIdAndVersaoOrderByOrdemAsc(produtoId, versaoOrigem);

        if (roteiroOrigem.isEmpty()) {
            throw new ResourceNotFoundException("Versão origem não encontrada");
        }

        if (roteiroRepository.existsByProdutoIdAndVersao(produtoId, versaoDestino)) {
            throw new BusinessException("Versão destino já existe");
        }

        var produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        for (RoteiroProducao item : roteiroOrigem) {
            RoteiroProducao novoItem = new RoteiroProducao();
            novoItem.setProduto(produto);
            novoItem.setVersao(versaoDestino);
            novoItem.setPostoTrabalho(item.getPostoTrabalho());
            novoItem.setOrdem(item.getOrdem());
            novoItem.setOperacaoDescricao(item.getOperacaoDescricao());
            novoItem.setTempoSetupMinutos(item.getTempoSetupMinutos());
            novoItem.setTempoOperacaoMinutos(item.getTempoOperacaoMinutos());
            novoItem.setTempoEsperaMinutos(item.getTempoEsperaMinutos());
            novoItem.setMaoObraNecessaria(item.getMaoObraNecessaria());
            novoItem.setFerramentas(item.getFerramentas());
            novoItem.setInstrucoes(item.getInstrucoes());

            roteiroRepository.save(novoItem);
        }
    }

    @Override
    public Map<String, Object> calcularTempoTotal(Long produtoId, String versao) {
        List<RoteiroProducao> roteiro = roteiroRepository
                .findByProdutoIdAndVersaoOrderByOrdemAsc(produtoId, versao);

        if (roteiro.isEmpty()) {
            throw new ResourceNotFoundException("Roteiro não encontrado");
        }

        int tempoSetupTotal = 0;
        int tempoOperacaoTotal = 0;
        int tempoEsperaTotal = 0;
        int maoObraTotal = 0;

        for (RoteiroProducao item : roteiro) {
            tempoSetupTotal += item.getTempoSetupMinutos() != null ? item.getTempoSetupMinutos() : 0;
            tempoOperacaoTotal += item.getTempoOperacaoMinutos() != null ? item.getTempoOperacaoMinutos() : 0;
            tempoEsperaTotal += item.getTempoEsperaMinutos() != null ? item.getTempoEsperaMinutos() : 0;
            maoObraTotal += item.getMaoObraNecessaria() != null ? item.getMaoObraNecessaria() : 0;
        }

        int tempoTotalMinutos = tempoSetupTotal + tempoOperacaoTotal + tempoEsperaTotal;

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("tempoSetupTotal", tempoSetupTotal);
        resultado.put("tempoOperacaoTotal", tempoOperacaoTotal);
        resultado.put("tempoEsperaTotal", tempoEsperaTotal);
        resultado.put("tempoTotalMinutos", tempoTotalMinutos);
        resultado.put("tempoTotalHoras", tempoTotalMinutos / 60.0);
        resultado.put("maoObraTotal", maoObraTotal);
        resultado.put("quantidadeOperacoes", roteiro.size());

        return resultado;
    }

    @Override
    public void validarRoteiro(RoteiroProducao roteiro) {
        if (roteiro.getProduto() == null || roteiro.getProduto().getId() == null) {
            throw new BusinessException("Produto é obrigatório");
        }

        if (roteiro.getVersao() == null || roteiro.getVersao().trim().isEmpty()) {
            throw new BusinessException("Versão é obrigatória");
        }

        if (roteiro.getOrdem() == null || roteiro.getOrdem() <= 0) {
            throw new BusinessException("Ordem deve ser maior que zero");
        }

        if (roteiro.getPostoTrabalho() != null && roteiro.getPostoTrabalho().getId() != null) {
            if (!postoTrabalhoRepository.existsById(roteiro.getPostoTrabalho().getId())) {
                throw new ResourceNotFoundException("Posto de trabalho não encontrado");
            }
        }

        if (roteiro.getTempoSetupMinutos() != null && roteiro.getTempoSetupMinutos() < 0) {
            throw new BusinessException("Tempo de setup não pode ser negativo");
        }

        if (roteiro.getTempoOperacaoMinutos() != null && roteiro.getTempoOperacaoMinutos() < 0) {
            throw new BusinessException("Tempo de operação não pode ser negativo");
        }
    }
}