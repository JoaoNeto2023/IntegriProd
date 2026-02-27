package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.Produto;
import com.inovs.integrprod.model.entity.ProdutoEstrutura;
import com.inovs.integrprod.repository.ProdutoEstruturaRepository;
import com.inovs.integrprod.repository.ProdutoRepository;
import com.inovs.integrprod.service.ProdutoEstruturaService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ProdutoEstruturaServiceImpl implements ProdutoEstruturaService {

    private final ProdutoEstruturaRepository estruturaRepository;
    private final ProdutoRepository produtoRepository;

    @Override
    public ProdutoEstrutura salvar(ProdutoEstrutura estrutura) {
        validarEstrutura(estrutura);

        // Verificar se produto pai existe
        if (!produtoRepository.existsById(estrutura.getProdutoPai().getId())) {
            throw new ResourceNotFoundException("Produto pai não encontrado");
        }

        // Verificar se produto filho existe
        if (!produtoRepository.existsById(estrutura.getProdutoFilho().getId())) {
            throw new ResourceNotFoundException("Produto componente não encontrado");
        }

        // Verificar se já existe componente igual na mesma versão
        List<ProdutoEstrutura> existentes = estruturaRepository
                .findByProdutoPaiIdAndVersao(estrutura.getProdutoPai().getId(), estrutura.getVersao());

        boolean componenteDuplicado = existentes.stream()
                .anyMatch(e -> e.getProdutoFilho().getId().equals(estrutura.getProdutoFilho().getId()));

        if (componenteDuplicado) {
            throw new BusinessException("Componente já existe na estrutura para esta versão");
        }

        return estruturaRepository.save(estrutura);
    }

    @Override
    public ProdutoEstrutura buscarPorId(Long id) {
        return estruturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estrutura não encontrada com ID: " + id));
    }

    @Override
    public List<ProdutoEstrutura> listarPorProdutoEversao(Long produtoId, String versao) {
        return estruturaRepository.findByProdutoPaiIdAndVersao(produtoId, versao);
    }

    @Override
    public List<ProdutoEstrutura> listarEstruturaVigente(Long produtoId, String versao, LocalDate data) {
        return estruturaRepository.findEstruturaVigente(produtoId, versao, data);
    }

    @Override
    public List<String> listarVersoes(Long produtoId) {
        return estruturaRepository.findVersoesByProdutoPaiId(produtoId);
    }

    @Override
    public List<ProdutoEstrutura> listarOndeProdutoEComponente(Long produtoId) {
        return estruturaRepository.findByProdutoFilhoId(produtoId);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        if (!estruturaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Estrutura não encontrada");
        }
        estruturaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProdutoEstrutura atualizar(Long id, ProdutoEstrutura estruturaAtualizada) {
        ProdutoEstrutura estruturaExistente = buscarPorId(id);

        estruturaExistente.setQuantidade(estruturaAtualizada.getQuantidade());
        estruturaExistente.setUnidadeMedida(estruturaAtualizada.getUnidadeMedida());
        estruturaExistente.setNivel(estruturaAtualizada.getNivel());
        estruturaExistente.setTipoComponente(estruturaAtualizada.getTipoComponente());
        estruturaExistente.setPercPerda(estruturaAtualizada.getPercPerda());
        estruturaExistente.setOrdemProducao(estruturaAtualizada.getOrdemProducao());
        estruturaExistente.setDataInicioVigencia(estruturaAtualizada.getDataInicioVigencia());
        estruturaExistente.setDataFimVigencia(estruturaAtualizada.getDataFimVigencia());

        return estruturaRepository.save(estruturaExistente);
    }

    @Override
    @Transactional
    public void copiarVersao(Long produtoId, String versaoOrigem, String versaoDestino, String usuario) {
        List<ProdutoEstrutura> estruturaOrigem = estruturaRepository
                .findByProdutoPaiIdAndVersao(produtoId, versaoOrigem);

        if (estruturaOrigem.isEmpty()) {
            throw new ResourceNotFoundException("Versão origem não encontrada");
        }

        if (estruturaRepository.existsByProdutoPaiIdAndVersao(produtoId, versaoDestino)) {
            throw new BusinessException("Versão destino já existe");
        }

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        for (ProdutoEstrutura item : estruturaOrigem) {
            ProdutoEstrutura novoItem = new ProdutoEstrutura();
            novoItem.setProdutoPai(produto);
            novoItem.setProdutoFilho(item.getProdutoFilho());
            novoItem.setVersao(versaoDestino);
            novoItem.setQuantidade(item.getQuantidade());
            novoItem.setUnidadeMedida(item.getUnidadeMedida());
            novoItem.setNivel(item.getNivel());
            novoItem.setTipoComponente(item.getTipoComponente());
            novoItem.setPercPerda(item.getPercPerda());
            novoItem.setOrdemProducao(item.getOrdemProducao());
            novoItem.setDataInicioVigencia(LocalDate.now());
            novoItem.setUsuarioCriacao(usuario);

            estruturaRepository.save(novoItem);
        }
    }

    @Override
    public Map<String, Object> calcularCustoEstrutura(Long produtoId, String versao) {
        List<ProdutoEstrutura> estrutura = estruturaRepository
                .findByProdutoPaiIdAndVersao(produtoId, versao);

        if (estrutura.isEmpty()) {
            throw new ResourceNotFoundException("Estrutura não encontrada");
        }

        BigDecimal custoTotal = BigDecimal.ZERO;
        Map<String, BigDecimal> detalhes = new HashMap<>();

        for (ProdutoEstrutura item : estrutura) {
            Produto componente = item.getProdutoFilho();
            BigDecimal custoComponente = componente.getCustoUnitario() != null ?
                    componente.getCustoUnitario() : BigDecimal.ZERO;

            BigDecimal quantidade = item.getQuantidade();
            BigDecimal perda = item.getPercPerda() != null ?
                    item.getPercPerda() : BigDecimal.ZERO;

            // Ajustar quantidade com perda
            BigDecimal quantidadeComPerda = quantidade.multiply(
                    BigDecimal.ONE.add(perda.divide(BigDecimal.valueOf(100)))
            );

            BigDecimal custoItem = custoComponente.multiply(quantidadeComPerda);
            custoTotal = custoTotal.add(custoItem);

            detalhes.put(componente.getCodigo() + " - " + componente.getNome(), custoItem);
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("custoTotal", custoTotal);
        resultado.put("detalhes", detalhes);
        resultado.put("quantidadeItens", estrutura.size());

        return resultado;
    }

    @Override
    public void validarEstrutura(ProdutoEstrutura estrutura) {
        if (estrutura.getProdutoPai() == null || estrutura.getProdutoPai().getId() == null) {
            throw new BusinessException("Produto pai é obrigatório");
        }

        if (estrutura.getProdutoFilho() == null || estrutura.getProdutoFilho().getId() == null) {
            throw new BusinessException("Produto componente é obrigatório");
        }

        if (estrutura.getVersao() == null || estrutura.getVersao().trim().isEmpty()) {
            throw new BusinessException("Versão é obrigatória");
        }

        if (estrutura.getQuantidade() == null || estrutura.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Quantidade deve ser maior que zero");
        }

        if (estrutura.getDataInicioVigencia() == null) {
            throw new BusinessException("Data de início de vigência é obrigatória");
        }

        if (estrutura.getProdutoPai().getId().equals(estrutura.getProdutoFilho().getId())) {
            throw new BusinessException("Um produto não pode ser componente dele mesmo");
        }
    }
}