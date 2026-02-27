package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "roteiro_producao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoteiroProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "versao", nullable = false, length = 20)
    private String versao;

    @ManyToOne
    @JoinColumn(name = "posto_trabalho_id")
    private PostoTrabalho postoTrabalho;

    @Column(name = "ordem", nullable = false)
    private Integer ordem;

    @Column(name = "operacao_descricao")
    private String operacaoDescricao;

    @Column(name = "tempo_setup_minutos")
    private Integer tempoSetupMinutos;

    @Column(name = "tempo_operacao_minutos")
    private Integer tempoOperacaoMinutos;

    @Column(name = "tempo_espera_minutos")
    private Integer tempoEsperaMinutos;

    @Column(name = "mao_obra_necessaria")
    private Integer maoObraNecessaria = 1;

    @Column(name = "ferramentas")
    private String ferramentas;

    @Column(name = "instrucoes")
    private String instrucoes;
}