package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consumo_material")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumoMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordem_producao_id", nullable = false)
    private OrdemProducao ordemProducao;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto; // Matéria prima consumida

    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque; // De qual lote foi retirado

    @Column(name = "quantidade_planejada", precision = 15, scale = 3)
    private BigDecimal quantidadePlanejada;

    @Column(name = "quantidade_consumida", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantidadeConsumida;

    @Column(name = "quantidade_devolvida", precision = 15, scale = 3)
    private BigDecimal quantidadeDevolvida = BigDecimal.ZERO;

    @Column(name = "data_consumo")
    private LocalDateTime dataConsumo;

    @Column(name = "usuario_consumo", length = 100)
    private String usuarioConsumo;

    @Column(name = "valor_unitario", precision = 15, scale = 4)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", precision = 15, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "movimento_estoque_id")
    private MovimentoEstoque movimentoEstoque;

    @PrePersist
    protected void onCreate() {
        dataConsumo = LocalDateTime.now();
    }
}
