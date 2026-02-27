package com.inovs.integrprod.repository;

import com.inovs.integrprod.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByCpfCnpj(String cpfCnpj);

    boolean existsByCpfCnpj(String cpfCnpj);

    List<Pessoa> findByTipoPessoa(String tipoPessoa);

    List<Pessoa> findByNomeRazaoContainingIgnoreCase(String nome);

    List<Pessoa> findBySituacao(String situacao);

    List<Pessoa> findByDataNascimentoBetween(LocalDate inicio, LocalDate fim);

    @Query("SELECT p FROM Pessoa p WHERE p.email = :email")
    Optional<Pessoa> findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Pessoa p WHERE p.cidade = :cidade")
    List<Pessoa> findByCidade(@Param("cidade") String cidade);

    @Query("SELECT p FROM Pessoa p WHERE p.uf = :uf")
    List<Pessoa> findByUf(@Param("uf") String uf);

    // Buscar pessoas que são clientes (via classificacao)
    @Query("SELECT p FROM Pessoa p JOIN p.classificacoes c WHERE c.tipo = 'CLIENTE'")
    List<Pessoa> findClientes();

    // Buscar pessoas que são fornecedores
    @Query("SELECT p FROM Pessoa p JOIN p.classificacoes c WHERE c.tipo = 'FORNECEDOR'")
    List<Pessoa> findFornecedores();

    // Buscar pessoas que são funcionários
    @Query("SELECT p FROM Pessoa p JOIN p.classificacoes c WHERE c.tipo = 'FUNCIONARIO'")
    List<Pessoa> findFuncionarios();
}