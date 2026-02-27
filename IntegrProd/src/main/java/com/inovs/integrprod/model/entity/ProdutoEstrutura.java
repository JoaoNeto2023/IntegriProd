package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "produto_estrutura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoEstrutura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_pai_id", nullable = false)
    private Produto produtoPai; // O produto que será produzido

    @ManyToOne
    @JoinColumn(name = "produto_filho_id", nullable = false)
    private Produto produtoFilho; // O componente/matéria-prima

    @Column(name = "versao", nullable = false, length = 20)
    private String versao;

    @Column(name = "quantidade", nullable = false, precision = 15, scale = 4)
    private BigDecimal quantidade;

    @ManyToOne
    @JoinColumn(name = "unidade_medida", referencedColumnName = "sigla")
    private UnidadeMedida unidadeMedida;

    @Column(name = "nivel")
    private Integer nivel; // Nível na estrutura (para produtos complexos)

    @Column(name = "tipo_componente", length = 20)
    private String tipoComponente; // MP, EMBALAGEM, INSUMO, SUBPRODUTO

    @Column(name = "perc_perda", precision = 5, scale = 2)
    private BigDecimal percPerda = BigDecimal.ZERO;

    @Column(name = "ordem_producao")
    private Integer ordemProducao; // Ordem de uso no processo

    @Column(name = "data_inicio_vigencia", nullable = false)
    private LocalDate dataInicioVigencia;

    @Column(name = "data_fim_vigencia")
    private LocalDate dataFimVigencia;

    @Column(name = "usuario_criacao", length = 100)
    private String usuarioCriacao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }
}