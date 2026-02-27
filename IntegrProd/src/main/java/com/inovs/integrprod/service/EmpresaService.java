package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.Empresa;
import java.util.List;

public interface EmpresaService {

    Empresa salvar(Empresa empresa);

    Empresa buscarPorId(Long id);

    Empresa buscarPorCnpj(String cnpj);

    List<Empresa> listarTodas();

    List<Empresa> listarAtivas();

    List<Empresa> buscarPorRazaoSocial(String razaoSocial);

    List<Empresa> buscarPorRegimeTributario(String regime);

    void deletar(Long id);

    Empresa atualizar(Long id, Empresa empresa);

    void validarEmpresa(Empresa empresa);
}