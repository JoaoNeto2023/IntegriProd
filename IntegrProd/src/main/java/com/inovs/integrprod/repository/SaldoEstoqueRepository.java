package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.SaldoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaldoEstoqueRepository extends JpaRepository<SaldoEstoque, Long> {

    Optional<SaldoEstoque> findByFilialIdAndProdutoIdAndDataSaldo(Long filialId, Long produtoId, LocalDate dataSaldo);

    List<SaldoEstoque> findByFilialIdAndDataSaldoBetween(Long filialId, LocalDate inicio, LocalDate fim);

    List<SaldoEstoque> findByProdutoIdAndDataSaldoBetween(Long produtoId, LocalDate inicio, LocalDate fim);

    @Query("SELECT s FROM SaldoEstoque s WHERE s.filial.id = :filialId AND s.dataSaldo = :dataSaldo")
    List<SaldoEstoque> findSaldosDiarios(@Param("filialId") Long filialId, @Param("dataSaldo") LocalDate dataSaldo);

    @Query("SELECT MAX(s.dataSaldo) FROM SaldoEstoque s WHERE s.filial.id = :filialId AND s.produto.id = :produtoId")
    LocalDate findUltimaDataSaldo(@Param("filialId") Long filialId, @Param("produtoId") Long produtoId);
}