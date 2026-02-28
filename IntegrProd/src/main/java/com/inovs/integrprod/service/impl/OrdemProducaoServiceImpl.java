package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.OrdemProducao;
import com.inovs.integrprod.repository.OrdemProducaoRepository;
import com.inovs.integrprod.repository.FilialRepository;
import com.inovs.integrprod.repository.ProdutoRepository;
import com.inovs.integrprod.repository.PessoaRepository;
import com.inovs.integrprod.service.OrdemProducaoService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdemProducaoServiceImpl implements OrdemProducaoService {

    private final OrdemProducaoRepository ordemRepository;
    private final FilialRepository filialRepository;
    private final ProdutoRepository produtoRepository;
    private final PessoaRepository pessoaRepository;

    @Override
    public OrdemProducao salvar(OrdemProducao ordemProducao) {
        validarOrdem(ordemProducao);

        if (ordemRepository.existsByNumeroOp(ordemProducao.getNumeroOp())) {
            throw new BusinessException("Já existe uma ordem com o número: " + ordemProducao.getNumeroOp());
        }

        // Buscar entidades relacionadas
        if (ordemProducao.getFilial() != null && ordemProducao.getFilial().getId() != null) {
            filialRepository.findById(ordemProducao.getFilial().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada"));
        }

        if (ordemProducao.getProduto() != null && ordemProducao.getProduto().getId() != null) {
            produtoRepository.findById(ordemProducao.getProduto().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        }

        if (ordemProducao.getResponsavel() != null && ordemProducao.getResponsavel().getId() != null) {
            pessoaRepository.findById(ordemProducao.getResponsavel().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Responsável não encontrado"));
        }

        return ordemRepository.save(ordemProducao);
    }

    @Override
    public OrdemProducao buscarPorId(Long id) {
        return ordemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordem de produção não encontrada com ID: " + id));
    }

    @Override
    public List<OrdemProducao> listarTodas() {
        return ordemRepository.findAll();
    }

    @Override
    public List<OrdemProducao> buscarPorFilial(Long filialId) {
        return ordemRepository.findByFilialId(filialId);
    }

    @Override
    public List<OrdemProducao> buscarPorProduto(Long produtoId) {
        return ordemRepository.findByProdutoId(produtoId);
    }

    @Override
    public List<OrdemProducao> buscarPorStatus(String status) {
        return ordemRepository.findByStatus(status);
    }

    @Override
    public List<OrdemProducao> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return ordemRepository.findByDataEmissaoBetween(inicio, fim);
    }

    @Override
    public List<OrdemProducao> buscarAbertas() {
        return ordemRepository.findOrdensAbertas();
    }

    @Override
    public List<OrdemProducao> buscarAtrasadas() {
        return ordemRepository.findOrdensAtrasadas();
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        OrdemProducao ordem = buscarPorId(id);
        ordemRepository.delete(ordem);
    }

    @Override
    @Transactional
    public OrdemProducao atualizar(Long id, OrdemProducao ordemAtualizada) {
        OrdemProducao ordemExistente = buscarPorId(id);

        ordemExistente.setQuantidadePlanejada(ordemAtualizada.getQuantidadePlanejada());
        ordemExistente.setDataInicioPrevista(ordemAtualizada.getDataInicioPrevista());
        ordemExistente.setDataFimPrevista(ordemAtualizada.getDataFimPrevista());
        ordemExistente.setPrioridade(ordemAtualizada.getPrioridade());
        ordemExistente.setResponsavel(ordemAtualizada.getResponsavel());
        ordemExistente.setObservacoes(ordemAtualizada.getObservacoes());

        return ordemRepository.save(ordemExistente);
    }

    @Override
    @Transactional
    public void iniciar(Long id) {
        OrdemProducao ordem = buscarPorId(id);

        if (!"PLANEJADA".equals(ordem.getStatus()) && !"LIBERADA".equals(ordem.getStatus())) {
            throw new BusinessException("Apenas ordens planejadas ou liberadas podem ser iniciadas");
        }

        ordem.setStatus("EM_PRODUCAO");
        ordem.setDataInicioReal(LocalDate.now());
        ordemRepository.save(ordem);
    }

    @Override
    @Transactional
    public void pausar(Long id) {
        OrdemProducao ordem = buscarPorId(id);

        if (!"EM_PRODUCAO".equals(ordem.getStatus())) {
            throw new BusinessException("Apenas ordens em produção podem ser pausadas");
        }

        ordem.setStatus("PAUSADA");
        ordemRepository.save(ordem);
    }

    @Override
    @Transactional
    public void concluir(Long id) {
        OrdemProducao ordem = buscarPorId(id);

        if (!"EM_PRODUCAO".equals(ordem.getStatus())) {
            throw new BusinessException("Apenas ordens em produção podem ser concluídas");
        }

        ordem.setStatus("CONCLUIDA");
        ordem.setDataFimReal(LocalDate.now());
        ordemRepository.save(ordem);
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        OrdemProducao ordem = buscarPorId(id);

        ordem.setStatus("CANCELADA");
        ordemRepository.save(ordem);
    }

    @Override
    public void validarOrdem(OrdemProducao ordemProducao) {
        if (ordemProducao.getNumeroOp() == null || ordemProducao.getNumeroOp().trim().isEmpty()) {
            throw new BusinessException("Número da ordem é obrigatório");
        }

        if (ordemProducao.getFilial() == null || ordemProducao.getFilial().getId() == null) {
            throw new BusinessException("Filial é obrigatória");
        }

        if (ordemProducao.getProduto() == null || ordemProducao.getProduto().getId() == null) {
            throw new BusinessException("Produto é obrigatório");
        }

        if (ordemProducao.getQuantidadePlanejada() == null ||
                ordemProducao.getQuantidadePlanejada().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Quantidade planejada deve ser maior que zero");
        }

        if (ordemProducao.getDataEmissao() == null) {
            throw new BusinessException("Data de emissão é obrigatória");
        }
    }
}