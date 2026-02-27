package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

    List<Empresa> findByRazaoSocialContainingIgnoreCase(String razaoSocial);

    List<Empresa> findByNomeFantasiaContainingIgnoreCase(String nomeFantasia);

    List<Empresa> findByStatus(String status);

    @Query("SELECT e FROM Empresa e WHERE e.regimeTributario = :regime")
    List<Empresa> findByRegimeTributario(@Param("regime") String regime);
}