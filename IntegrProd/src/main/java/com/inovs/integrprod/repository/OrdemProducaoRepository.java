package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.OrdemProducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Long> {

    List<OrdemProducao> findByFilialId(Long filialId);

    List<OrdemProducao> findByProdutoId(Long produtoId);

    List<OrdemProducao> findByStatus(String status);

    List<OrdemProducao> findByDataEmissaoBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT o FROM OrdemProducao o WHERE o.status IN ('PLANEJADA', 'LIBERADA', 'EM_PRODUCAO')")
    List<OrdemProducao> findOrdensAbertas();

    @Query("SELECT o FROM OrdemProducao o WHERE o.dataFimPrevista < CURRENT_DATE AND o.status != 'CONCLUIDA'")
    List<OrdemProducao> findOrdensAtrasadas();

    boolean existsByNumeroOp(String numeroOp);
}