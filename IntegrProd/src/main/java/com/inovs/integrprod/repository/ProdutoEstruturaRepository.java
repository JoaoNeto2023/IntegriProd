package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.ProdutoEstrutura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoEstruturaRepository extends JpaRepository<ProdutoEstrutura, Long> {

    // Buscar estrutura de um produto (pai) por versão
    List<ProdutoEstrutura> findByProdutoPaiIdAndVersao(Long produtoPaiId, String versao);

    // Buscar estrutura vigente (data dentro do período)
    @Query("SELECT pe FROM ProdutoEstrutura pe WHERE pe.produtoPai.id = :produtoId " +
            "AND pe.versao = :versao AND pe.dataInicioVigencia <= :data " +
            "AND (pe.dataFimVigencia IS NULL OR pe.dataFimVigencia >= :data)")
    List<ProdutoEstrutura> findEstruturaVigente(
            @Param("produtoId") Long produtoId,
            @Param("versao") String versao,
            @Param("data") LocalDate data);

    // Buscar onde um produto é usado como componente
    List<ProdutoEstrutura> findByProdutoFilhoId(Long produtoFilhoId);

    // Buscar versões de uma estrutura
    @Query("SELECT DISTINCT pe.versao FROM ProdutoEstrutura pe WHERE pe.produtoPai.id = :produtoId")
    List<String> findVersoesByProdutoPaiId(@Param("produtoId") Long produtoId);

    // Verificar se existe versão
    boolean existsByProdutoPaiIdAndVersao(Long produtoPaiId, String versao);

    // Buscar componentes por tipo
    List<ProdutoEstrutura> findByProdutoPaiIdAndTipoComponente(Long produtoPaiId, String tipoComponente);
}