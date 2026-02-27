package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "pessoa_classificacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PessoaClassificacaoId.class)
public class PessoaClassificacao {

    @Id
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @Id
    @Column(name = "tipo", length = 20)
    private String tipo; // CLIENTE, FORNECEDOR, FUNCIONARIO, TRANSPORTADOR

    @Column(name = "limite_credito", precision = 15, scale = 2)
    private BigDecimal limiteCredito;

    @Column(name = "dias_prazo")
    private Integer diasPrazo;

    @Column(name = "desconto_maximo", precision = 5, scale = 2)
    private BigDecimal descontoMaximo;
}