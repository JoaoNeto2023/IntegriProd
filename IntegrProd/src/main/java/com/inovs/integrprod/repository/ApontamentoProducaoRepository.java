package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.ApontamentoProducao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApontamentoProducaoRepository extends JpaRepository<ApontamentoProducao, Long> {

    List<ApontamentoProducao> findByOrdemProducaoId(Long ordemProducaoId);

    List<ApontamentoProducao> findByPostoTrabalhoId(Long postoTrabalhoId);

    List<ApontamentoProducao> findByDataApontamento(LocalDate dataApontamento);

    List<ApontamentoProducao> findByDataApontamentoBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT a FROM ApontamentoProducao a WHERE a.ordemProducao.id = :ordemId AND a.tipoApontamento = 'PRODUCAO'")
    List<ApontamentoProducao> findApontamentosProducao(@Param("ordemId") Long ordemId);

    @Query("SELECT SUM(a.quantidadeProduzida) FROM ApontamentoProducao a WHERE a.ordemProducao.id = :ordemId")
    Double sumQuantidadeProduzidaByOrdemId(@Param("ordemId") Long ordemId);
}