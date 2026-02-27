package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.RoteiroProducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoteiroProducaoRepository extends JpaRepository<RoteiroProducao, Long> {

    List<RoteiroProducao> findByProdutoIdAndVersaoOrderByOrdemAsc(Long produtoId, String versao);

    boolean existsByProdutoIdAndVersao(Long produtoId, String versao);

    @Query("SELECT DISTINCT r.versao FROM RoteiroProducao r WHERE r.produto.id = :produtoId")
    List<String> findVersoesByProdutoId(@Param("produtoId") Long produtoId);

    List<RoteiroProducao> findByPostoTrabalhoId(Long postoTrabalhoId);
}