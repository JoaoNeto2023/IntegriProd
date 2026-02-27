package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.Filial;
import java.util.List;

public interface FilialService {

    Filial salvar(Filial filial);

    Filial buscarPorId(Long id);

    Filial buscarPorCnpj(String cnpj);

    Filial buscarPorCodigo(String codigo);

    List<Filial> listarTodas();

    List<Filial> listarAtivas();

    List<Filial> buscarPorEmpresa(Long empresaId);

    List<Filial> buscarPorNome(String nome);

    List<Filial> buscarPorUf(String uf);

    List<Filial> buscarPorCidade(String cidade);

    void deletar(Long id);

    Filial atualizar(Long id, Filial filial);

    void validarFilial(Filial filial);
}