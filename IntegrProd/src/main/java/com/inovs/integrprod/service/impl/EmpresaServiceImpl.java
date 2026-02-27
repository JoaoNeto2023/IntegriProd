package com.inovs.integrprod.service.impl;

import com.inovs.integrprod.model.entity.Empresa;
import com.inovs.integrprod.repository.EmpresaRepository;
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
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Override
    public Empresa salvar(Empresa empresa) {
        validarEmpresa(empresa);

        if (empresaRepository.existsByCnpj(empresa.getCnpj())) {
            throw new BusinessException("Já existe uma empresa com o CNPJ: " + empresa.getCnpj());
        }

        if (empresa.getStatus() == null) {
            empresa.setStatus("ATIVO");
        }

        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com ID: " + id));
    }

    @Override
    public Empresa buscarPorCnpj(String cnpj) {
        return empresaRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada com CNPJ: " + cnpj));
    }

    @Override
    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    @Override
    public List<Empresa> listarAtivas() {
        return empresaRepository.findByStatus("ATIVO");
    }

    @Override
    public List<Empresa> buscarPorRazaoSocial(String razaoSocial) {
        return empresaRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial);
    }

    @Override
    public List<Empresa> buscarPorRegimeTributario(String regime) {
        return empresaRepository.findByRegimeTributario(regime);
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Empresa empresa = buscarPorId(id);

        // Verificar se tem filiais associadas
        // if (filialRepository.existsByEmpresaId(id)) {
        //     throw new BusinessException("Não é possível excluir empresa com filiais associadas");
        // }

        empresaRepository.delete(empresa);
    }

    @Override
    @Transactional
    public Empresa atualizar(Long id, Empresa empresaAtualizada) {
        Empresa empresaExistente = buscarPorId(id);

        if (!empresaExistente.getCnpj().equals(empresaAtualizada.getCnpj()) &&
                empresaRepository.existsByCnpj(empresaAtualizada.getCnpj())) {
            throw new BusinessException("Já existe uma empresa com o CNPJ: " + empresaAtualizada.getCnpj());
        }

        empresaExistente.setRazaoSocial(empresaAtualizada.getRazaoSocial());
        empresaExistente.setNomeFantasia(empresaAtualizada.getNomeFantasia());
        empresaExistente.setCnpj(empresaAtualizada.getCnpj());
        empresaExistente.setInscricaoEstadual(empresaAtualizada.getInscricaoEstadual());
        empresaExistente.setInscricaoMunicipal(empresaAtualizada.getInscricaoMunicipal());
        empresaExistente.setRegimeTributario(empresaAtualizada.getRegimeTributario());
        empresaExistente.setStatus(empresaAtualizada.getStatus());

        return empresaRepository.save(empresaExistente);
    }

    @Override
    public void validarEmpresa(Empresa empresa) {
        if (empresa.getRazaoSocial() == null || empresa.getRazaoSocial().trim().isEmpty()) {
            throw new BusinessException("Razão social é obrigatória");
        }

        if (empresa.getCnpj() == null || empresa.getCnpj().trim().isEmpty()) {
            throw new BusinessException("CNPJ é obrigatório");
        }

        // Validar formato do CNPJ (simplificado)
        String cnpjLimpo = empresa.getCnpj().replaceAll("[^0-9]", "");
        if (cnpjLimpo.length() != 14) {
            throw new BusinessException("CNPJ inválido. Deve conter 14 dígitos.");
        }
    }
}
