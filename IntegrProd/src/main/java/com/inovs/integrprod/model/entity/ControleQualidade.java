package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "controle_qualidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControleQualidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordem_producao_id", nullable = false)
    private OrdemProducao ordemProducao;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "lote", length = 50)
    private String lote;

    @Column(name = "data_inspecao", nullable = false)
    private LocalDateTime dataInspecao;

    @ManyToOne
    @JoinColumn(name = "inspetor_id")
    private Pessoa inspetor;

    @Column(name = "quantidade_inspecionada", precision = 15, scale = 3)
    private BigDecimal quantidadeInspecionada;

    @Column(name = "quantidade_aprovada", precision = 15, scale = 3)
    private BigDecimal quantidadeAprovada;

    @Column(name = "quantidade_reprovada", precision = 15, scale = 3)
    private BigDecimal quantidadeReprovada;

    @Column(name = "resultado", length = 20)
    private String resultado; // APROVADO, REPROVADO, RETRABALHO

    @Column(name = "tipo_defeito", length = 100)
    private String tipoDefeito;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "acao_corretiva")
    private String acaoCorretiva;
}