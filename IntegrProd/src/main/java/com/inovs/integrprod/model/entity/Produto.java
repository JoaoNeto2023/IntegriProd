package com.inovs.integrprod.model.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name= "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 30)
    private String codigo;

    @Column(name = "codigo_barras", length = 50)
    private String codigoBarras;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "tipo_produto_id")
    private TipoProduto tipoProduto;

    @ManyToOne
    @JoinColumn(name = "familia_id")
    private FamiliaProduto familia;

    @ManyToOne
    @JoinColumn(name = "unidade_medida", referencedColumnName = "sigla")
    private UnidadeMedida unidadeMedida;

    @Column(name = "ncm", length = 10)
    private String ncm;

    @Column(name = "cest", length = 10)
    private String cest;

    @Column(name = "origem", length = 1)
    private String origem;

    @Column(name = "tipo_item", length = 2)
    private String tipoItem;

    @Column(name = "peso_bruto", precision = 15, scale = 3)
    private BigDecimal pesoBruto;

    @Column(name = "peso_liquido", precision = 15, scale = 3)
    private BigDecimal pesoLiquido;

    @Column(name = "controla_lote")
    private Boolean controlaLote = false;

    @Column(name = "controla_validade")
    private Boolean controlaValidade = false;

    @Column(name = "dias_validade")
    private Integer diasValidade;

    @Column(name = "estoque_minimo", precision = 15, scale = 3)
    private BigDecimal estoqueMinimo;

    @Column(name = "estoque_maximo", precision = 15, scale = 3)
    private BigDecimal estoqueMaximo;

    @Column(name = "ponto_pedido", precision = 15, scale = 3)
    private BigDecimal pontoPedido;

    @Column(name = "custo_unitario", precision = 15, scale = 4)
    private BigDecimal custoUnitario;

    @Column(name = "custo_ultima_compra", precision = 15, scale = 4)
    private BigDecimal custoUltimaCompra;

    @Column(name = "custo_medio", precision = 15, scale = 4)
    private BigDecimal custoMedio;

    @Column(name = "markup_percentual", precision = 5, scale = 2)
    private BigDecimal markupPercentual;

    @Column(name = "preco_venda", precision = 15, scale = 2)
    private BigDecimal precoVenda;

    @Column(name = "status", length = 20)
    private String status = "ATIVO";

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "usuario_cadastro", length = 100)
    private String usuarioCadastro;

    @PrePersist
    protected void onCreate() {
        dataCadastro = LocalDateTime.now();
    }

}
