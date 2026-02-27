package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "saldo_estoque", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"filial_id", "produto_id", "data_saldo"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaldoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "data_saldo", nullable = false)
    private LocalDate dataSaldo;

    @Column(name = "saldo_inicial", precision = 15, scale = 3)
    private BigDecimal saldoInicial = BigDecimal.ZERO;

    @Column(name = "entradas", precision = 15, scale = 3)
    private BigDecimal entradas = BigDecimal.ZERO;

    @Column(name = "saidas", precision = 15, scale = 3)
    private BigDecimal saidas = BigDecimal.ZERO;

    @Column(name = "saldo_final", precision = 15, scale = 3)
    private BigDecimal saldoFinal = BigDecimal.ZERO;

    @Column(name = "valor_total", precision = 15, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "data_calculado")
    private LocalDateTime dataCalculado;

    @PrePersist
    protected void onCreate() {
        dataCalculado = LocalDateTime.now();
    }
}