package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.ApontamentoProducao;
import com.inovs.integrprod.repository.ApontamentoProducaoRepository;
import com.inovs.integrprod.repository.OrdemProducaoRepository;
import com.inovs.integrprod.repository.PostoTrabalhoRepository;
import com.inovs.integrprod.repository.PessoaRepository;
import com.inovs.integrprod.service.ApontamentoProducaoService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApontamentoProducaoServiceImpl implements ApontamentoProducaoService {

    private final ApontamentoProducaoRepository apontamentoRepository;
    private final OrdemProducaoRepository ordemRepository;
    private final PostoTrabalhoRepository postoTrabalhoRepository;
    private final PessoaRepository pessoaRepository;

    @Override
    public ApontamentoProducao salvar(ApontamentoProducao apontamento) {
        validarApontamento(apontamento);

        // Verificar se a ordem existe
        if (apontamento.getOrdemProducao() != null && apontamento.getOrdemProducao().getId() != null) {
            ordemRepository.findById(apontamento.getOrdemProducao().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ordem de produção não encontrada"));
        }

        // Verificar se o posto de trabalho existe (se informado)
        if (apontamento.getPostoTrabalho() != null && apontamento.getPostoTrabalho().getId() != null) {
            postoTrabalhoRepository.findById(apontamento.getPostoTrabalho().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Posto de trabalho não encontrado"));
        }

        // Verificar se o funcionário existe (se informado)
        if (apontamento.getFuncionario() != null && apontamento.getFuncionario().getId() != null) {
            pessoaRepository.findById(apontamento.getFuncionario().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado"));
        }

        // Calcular tempo total se hora início e fim foram informadas
        if (apontamento.getHoraInicio() != null && apontamento.getHoraFim() != null) {
            long minutos = ChronoUnit.MINUTES.between(apontamento.getHoraInicio(), apontamento.getHoraFim());
            apontamento.setTempoTotalMinutos((int) minutos);
        }

        return apontamentoRepository.save(apontamento);
    }

    @Override
    public ApontamentoProducao buscarPorId(Long id) {
        return apontamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Apontamento não encontrado com ID: " + id));
    }

    @Override
    public List<ApontamentoProducao> listarTodos() {
        return apontamentoRepository.findAll();
    }

    @Override
    public List<ApontamentoProducao> buscarPorOrdem(Long ordemId) {
        return apontamentoRepository.findByOrdemProducaoId(ordemId);
    }

    @Override
    public List<ApontamentoProducao> buscarPorPostoTrabalho(Long postoTrabalhoId) {
        return apontamentoRepository.findByPostoTrabalhoId(postoTrabalhoId);
    }

    @Override
    public List<ApontamentoProducao> buscarPorData(LocalDate data) {
        return apontamentoRepository.findByDataApontamento(data);
    }

    @Override
    public List<ApontamentoProducao> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return apontamentoRepository.findByDataApontamentoBetween(inicio, fim);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        ApontamentoProducao apontamento = buscarPorId(id);
        apontamentoRepository.delete(apontamento);
    }

    @Override
    @Transactional
    public ApontamentoProducao atualizar(Long id, ApontamentoProducao apontamentoAtualizado) {
        ApontamentoProducao apontamentoExistente = buscarPorId(id);

        apontamentoExistente.setQuantidadeProduzida(apontamentoAtualizado.getQuantidadeProduzida());
        apontamentoExistente.setQuantidadeRefugo(apontamentoAtualizado.getQuantidadeRefugo());
        apontamentoExistente.setObservacoes(apontamentoAtualizado.getObservacoes());
        apontamentoExistente.setMotivoParada(apontamentoAtualizado.getMotivoParada());

        return apontamentoRepository.save(apontamentoExistente);
    }

    @Override
    public void validarApontamento(ApontamentoProducao apontamento) {
        if (apontamento.getOrdemProducao() == null || apontamento.getOrdemProducao().getId() == null) {
            throw new BusinessException("Ordem de produção é obrigatória");
        }

        if (apontamento.getDataApontamento() == null) {
            throw new BusinessException("Data do apontamento é obrigatória");
        }

        if (apontamento.getQuantidadeProduzida() == null ||
                apontamento.getQuantidadeProduzida().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Quantidade produzida deve ser maior que zero");
        }

        if (apontamento.getHoraInicio() != null && apontamento.getHoraFim() != null) {
            if (apontamento.getHoraFim().isBefore(apontamento.getHoraInicio())) {
                throw new BusinessException("Hora fim não pode ser anterior à hora início");
            }
        }
    }
}