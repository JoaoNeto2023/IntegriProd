package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimento_estoque")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "filial_id", nullable = false)
    private Filial filial;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    @Column(name = "tipo_movimento", nullable = false, length = 30)
    private String tipoMovimento; // COMPRA, VENDA, PRODUCAO_ENTRADA, PRODUCAO_SAIDA, AJUSTE_ENTRADA, AJUSTE_SAIDA, TRANSFERENCIA, DEVOLUCAO_COMPRA, DEVOLUCAO_VENDA, PERDA, AMOSTRA

    @Column(name = "documento_tipo", length = 30)
    private String documentoTipo; // NF, OP, etc

    @Column(name = "documento_numero", length = 50)
    private String documentoNumero;

    @Column(name = "quantidade", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantidade;

    @Column(name = "valor_unitario", precision = 15, scale = 4)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", precision = 15, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "lote_origem", length = 50)
    private String loteOrigem;

    @Column(name = "lote_destino", length = 50)
    private String loteDestino;

    @Column(name = "data_movimento")
    private LocalDateTime dataMovimento;

    @Column(name = "usuario_movimento", length = 100)
    private String usuarioMovimento;

    @Column(name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "movimento_origem_id")
    private MovimentoEstoque movimentoOrigem; // Para transferências/devoluções

    @PrePersist
    protected void onCreate() {
        dataMovimento = LocalDateTime.now();
    }
}