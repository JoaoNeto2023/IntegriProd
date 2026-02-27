package com.inovs.integrprod.service;

import com.inovs.integrprod.model.entity.PostoTrabalho;
import java.math.BigDecimal;
import java.util.List;

public interface PostoTrabalhoService {

    PostoTrabalho salvar(PostoTrabalho postoTrabalho);

    PostoTrabalho buscarPorId(Long id);

    PostoTrabalho buscarPorCodigo(String codigo);

    List<PostoTrabalho> listarTodos();

    List<PostoTrabalho> listarAtivos();

    List<PostoTrabalho> buscarPorNome(String nome);

    List<PostoTrabalho> buscarPorSetor(String setor);

    List<PostoTrabalho> buscarPorStatus(String status);

    void deletar(Long id);

    PostoTrabalho atualizar(Long id, PostoTrabalho postoTrabalho);

    PostoTrabalho atualizarCusto(Long id, BigDecimal custoHora, BigDecimal custoFixoHora);

    void validarPostoTrabalho(PostoTrabalho postoTrabalho);
}