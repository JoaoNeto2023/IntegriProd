package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Long> {

    Optional<Filial> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

    Optional<Filial> findByCodigo(String codigo);

    List<Filial> findByNomeContainingIgnoreCase(String nome);

    List<Filial> findByEmpresaId(Long empresaId);

    List<Filial> findByUf(String uf);

    List<Filial> findByCidade(String cidade);

    List<Filial> findByStatus(String status);

    @Query("SELECT f FROM Filial f WHERE f.empresa.id = :empresaId AND f.status = 'ATIVO'")
    List<Filial> findAtivasPorEmpresa(@Param("empresaId") Long empresaId);

    @Query("SELECT f FROM Filial f WHERE f.empresa.id = :empresaId AND f.codigo = :codigo")
    Optional<Filial> findByEmpresaIdAndCodigo(@Param("empresaId") Long empresaId, @Param("codigo") String codigo);
}