package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.PessoaClassificacao;
import com.inovs.integrprod.model.entity.PessoaClassificacaoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaClassificacaoRepository extends JpaRepository<PessoaClassificacao, PessoaClassificacaoId> {

    List<PessoaClassificacao> findByPessoaId(Long pessoaId);

    List<PessoaClassificacao> findByTipo(String tipo);

    @Query("SELECT pc FROM PessoaClassificacao pc WHERE pc.pessoa.id = :pessoaId AND pc.tipo = :tipo")
    Optional<PessoaClassificacao> findByPessoaIdAndTipo(@Param("pessoaId") Long pessoaId, @Param("tipo") String tipo);

    @Query("SELECT pc FROM PessoaClassificacao pc WHERE pc.tipo = :tipo AND pc.limiteCredito IS NOT NULL")
    List<PessoaClassificacao> findComLimiteCredito(@Param("tipo") String tipo);

    @Query("SELECT pc FROM PessoaClassificacao pc WHERE pc.diasPrazo > :dias")
    List<PessoaClassificacao> findComPrazoMaiorQue(@Param("dias") Integer dias);

    boolean existsByPessoaIdAndTipo(Long pessoaId, String tipo);

    @Query("SELECT COUNT(pc) FROM PessoaClassificacao pc WHERE pc.tipo = :tipo")
    long countByTipo(@Param("tipo") String tipo);
}