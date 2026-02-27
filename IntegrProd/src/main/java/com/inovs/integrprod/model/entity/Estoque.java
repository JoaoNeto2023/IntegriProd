package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "lote", length = 50)
    private String lote;

    @Column(name = "data_fabricacao")
    private LocalDate dataFabricacao;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Column(name = "quantidade", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantidade = BigDecimal.ZERO;

    @Column(name = "reservado", precision = 15, scale = 3)
    private BigDecimal reservado = BigDecimal.ZERO;

    @Column(name = "localizacao", length = 50)
    private String localizacao;

    @Column(name = "custo_unitario", precision = 15, scale = 4)
    private BigDecimal custoUnitario;

    @Column(name = "status_lote", length = 20)
    private String statusLote = "ATIVO"; // ATIVO, BLOQUEADO, VENCIDO, EM_ANALISE

    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;

    @Column(name = "documento_entrada", length = 50)
    private String documentoEntrada;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    protected void onCreate() {
        dataEntrada = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    // Método para calcular quantidade disponível (considerando reservado)
    public BigDecimal getDisponivel() {
        return quantidade.subtract(reservado != null ? reservado : BigDecimal.ZERO);
    }
}