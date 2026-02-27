package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordem_producao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdemProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;

    @Column(name = "numero_op", unique = true, nullable = false, length = 30)
    private String numeroOp;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "versao_estrutura", length = 20)
    private String versaoEstrutura;

    @Column(name = "quantidade_planejada", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantidadePlanejada;

    @Column(name = "quantidade_produzida", precision = 15, scale = 3)
    private BigDecimal quantidadeProduzida = BigDecimal.ZERO;

    @Column(name = "quantidade_aprovada", precision = 15, scale = 3)
    private BigDecimal quantidadeAprovada = BigDecimal.ZERO;

    @Column(name = "quantidade_refugo", precision = 15, scale = 3)
    private BigDecimal quantidadeRefugo = BigDecimal.ZERO;

    @Column(name = "data_emissao", nullable = false)
    private LocalDate dataEmissao;

    @Column(name = "data_inicio_prevista")
    private LocalDate dataInicioPrevista;

    @Column(name = "data_fim_prevista")
    private LocalDate dataFimPrevista;

    @Column(name = "data_inicio_real")
    private LocalDate dataInicioReal;

    @Column(name = "data_fim_real")
    private LocalDate dataFimReal;

    @Column(name = "prioridade")
    private Integer prioridade = 5;

    @Column(name = "status", length = 30)
    private String status = "PLANEJADA"; // PLANEJADA, LIBERADA, EM_PRODUCAO, PAUSADA, CONCLUIDA, CANCELADA

    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private Pessoa responsavel;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "custo_previsto_material", precision = 15, scale = 2)
    private BigDecimal custoPrevistoMaterial = BigDecimal.ZERO;

    @Column(name = "custo_previsto_mao_obra", precision = 15, scale = 2)
    private BigDecimal custoPrevistoMaoObra = BigDecimal.ZERO;

    @Column(name = "custo_previsto_total", precision = 15, scale = 2)
    private BigDecimal custoPrevistoTotal = BigDecimal.ZERO;

    @Column(name = "custo_real_material", precision = 15, scale = 2)
    private BigDecimal custoRealMaterial = BigDecimal.ZERO;

    @Column(name = "custo_real_mao_obra", precision = 15, scale = 2)
    private BigDecimal custoRealMaoObra = BigDecimal.ZERO;

    @Column(name = "custo_real_total", precision = 15, scale = 2)
    private BigDecimal custoRealTotal = BigDecimal.ZERO;

    @Column(name = "usuario_criacao", length = 100)
    private String usuarioCriacao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        if (dataEmissao == null) {
            dataEmissao = LocalDate.now();
        }
    }
}