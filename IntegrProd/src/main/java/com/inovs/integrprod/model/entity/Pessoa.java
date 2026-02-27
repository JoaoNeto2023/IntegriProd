//A classe corresponde a cadastros de Clientes, Fornecedores, Funcionários...

package com.inovs.integrprod.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pessoa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_pessoa", length = 2)
    private String tipoPessoa;

    @Column(name = "nome_razao", nullable = false, length = 200)
    private String nomeRazao;

    @Column(name = "nome_fantasia", length = 200)
    private String nomeFantasia;

    @Column(name = "cpf_cnpj", unique = true, nullable = false, length = 18)
    private String cpfCnpj;

    @Column(name = "rg_ie", length = 20)
    private String rgIe;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "sexo", length = 1)
    private String sexo;

    @Column(name = "estado_civil", length = 20)
    private String estadoCivil;

    @Column(name = "profissao", length = 100)
    private String profissao;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefone1", length = 20)
    private String telefone1;

    @Column(name = "telefone2", length = 20)
    private String telefone2;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "site", length = 100)
    private String site;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "situacao", length = 20)
    private String situacao = "ATIVO";

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PessoaClassificacao> classificacoes;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }
}