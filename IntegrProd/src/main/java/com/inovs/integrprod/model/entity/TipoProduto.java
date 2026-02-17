package com.inovs.integrprod.model.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tipo_produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 10)
    private String codigo;

    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;

    @Column(name = "natureza", length = 20)
    private String natureza;
}
