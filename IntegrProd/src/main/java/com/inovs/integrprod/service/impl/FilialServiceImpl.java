package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.Filial;
import com.inovs.integrprod.repository.FilialRepository;
import com.inovs.integrprod.service.FilialService;
import com.inovs.integrprod.service.EmpresaService;
import com.inovs.integrprod.exception.ResourceNotFoundException;
import com.inovs.integrprod.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FilialServiceImpl implements FilialService {

    private final FilialRepository filialRepository;
    private final EmpresaService empresaService;

    @Override
    public Filial salvar(Filial filial) {
        validarFilial(filial);

        // Validar se empresa existe
        if (filial.getEmpresa() != null && filial.getEmpresa().getId() != null) {
            empresaService.buscarPorId(filial.getEmpresa().getId());
        }

        if (filialRepository.existsByCnpj(filial.getCnpj())) {
            throw new BusinessException("Já existe uma filial com o CNPJ: " + filial.getCnpj());
        }

        // Verificar se código já existe na mesma empresa
        if (filial.getEmpresa() != null && filial.getEmpresa().getId() != null) {
            filialRepository.findByEmpresaIdAndCodigo(filial.getEmpresa().getId(), filial.getCodigo())
                    .ifPresent(f -> {
                        throw new BusinessException("Já existe uma filial com o código " + filial.getCodigo() + " nesta empresa");
                    });
        }

        if (filial.getStatus() == null) {
            filial.setStatus("ATIVO");
        }

        return filialRepository.save(filial);
    }

    @Override
    public Filial buscarPorId(Long id) {
        return filialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada com ID: " + id));
    }

    @Override
    public Filial buscarPorCnpj(String cnpj) {
        return filialRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada com CNPJ: " + cnpj));
    }

    @Override
    public Filial buscarPorCodigo(String codigo) {
        return filialRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada com código: " + codigo));
    }

    @Override
    public List<Filial> listarTodas() {
        return filialRepository.findAll();
    }

    @Override
    public List<Filial> listarAtivas() {
        return filialRepository.findByStatus("ATIVO");
    }

    @Override
    public List<Filial> buscarPorEmpresa(Long empresaId) {
        return filialRepository.findByEmpresaId(empresaId);
    }

    @Override
    public List<Filial> buscarPorNome(String nome) {
        return filialRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Filial> buscarPorUf(String uf) {
        return filialRepository.findByUf(uf);
    }

    @Override
    public List<Filial> buscarPorCidade(String cidade) {
        return filialRepository.findByCidade(cidade);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Filial filial = buscarPorId(id);

        // Verificar se tem estoque associado (quando implementarmos)
        // if (estoqueRepository.existsByFilialId(id)) {
        //     throw new BusinessException("Não é possível excluir filial com estoque associado");
        // }

        // Ao invés de deletar, apenas inativar
        filial.setStatus("INATIVO");
        filialRepository.save(filial);
    }

    @Override
    @Transactional
    public Filial atualizar(Long id, Filial filialAtualizada) {
        Filial filialExistente = buscarPorId(id);

        if (!filialExistente.getCnpj().equals(filialAtualizada.getCnpj()) &&
                filialRepository.existsByCnpj(filialAtualizada.getCnpj())) {
            throw new BusinessException("Já existe uma filial com o CNPJ: " + filialAtualizada.getCnpj());
        }

        filialExistente.setCodigo(filialAtualizada.getCodigo());
        filialExistente.setNome(filialAtualizada.getNome());
        filialExistente.setCnpj(filialAtualizada.getCnpj());
        filialExistente.setIe(filialAtualizada.getIe());
        filialExistente.setEndereco(filialAtualizada.getEndereco());
        filialExistente.setCidade(filialAtualizada.getCidade());
        filialExistente.setUf(filialAtualizada.getUf());
        filialExistente.setCep(filialAtualizada.getCep());
        filialExistente.setTelefone(filialAtualizada.getTelefone());
        filialExistente.setEmail(filialAtualizada.getEmail());
        filialExistente.setGerenteResponsavel(filialAtualizada.getGerenteResponsavel());
        filialExistente.setStatus(filialAtualizada.getStatus());

        return filialRepository.save(filialExistente);
    }

    @Override
    public void validarFilial(Filial filial) {
        if (filial.getCodigo() == null || filial.getCodigo().trim().isEmpty()) {
            throw new BusinessException("Código da filial é obrigatório");
        }

        if (filial.getNome() == null || filial.getNome().trim().isEmpty()) {
            throw new BusinessException("Nome da filial é obrigatório");
        }

        if (filial.getCnpj() == null || filial.getCnpj().trim().isEmpty()) {
            throw new BusinessException("CNPJ da filial é obrigatório");
        }

        if (filial.getEmpresa() == null || filial.getEmpresa().getId() == null) {
            throw new BusinessException("Empresa é obrigatória para a filial");
        }

        // Validar formato do CNPJ
        String cnpjLimpo = filial.getCnpj().replaceAll("[^0-9]", "");
        if (cnpjLimpo.length() != 14) {
            throw new BusinessException("CNPJ inválido. Deve conter 14 dígitos.");
        }
    }
}
