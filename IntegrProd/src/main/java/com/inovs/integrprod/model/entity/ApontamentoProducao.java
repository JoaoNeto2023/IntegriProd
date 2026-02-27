package com.inovs.integrprod.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "apontamento_producao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApontamentoProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ordem_producao_id", nullable = false)
    private OrdemProducao ordemProducao;

    @ManyToOne
    @JoinColumn(name = "posto_trabalho_id")
    private PostoTrabalho postoTrabalho;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Pessoa funcionario;

    @Column(name = "data_apontamento", nullable = false)
    private LocalDate dataApontamento;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fim")
    private LocalTime horaFim;

    @Column(name = "tempo_total_minutos")
    private Integer tempoTotalMinutos;

    @Column(name = "quantidade_produzida", nullable = false, precision = 15, scale = 3)
    private BigDecimal quantidadeProduzida;

    @Column(name = "quantidade_refugo", precision = 15, scale = 3)
    private BigDecimal quantidadeRefugo = BigDecimal.ZERO;

    @Column(name = "tipo_apontamento", length = 30)
    private String tipoApontamento; // PRODUCAO, SETUP, PARADA, MANUTENCAO

    @Column(name = "motivo_parada", length = 100)
    private String motivoParada;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    @Column(name = "usuario_registro", length = 100)
    private String usuarioRegistro;

    @PrePersist
    protected void onCreate() {
        dataRegistro = LocalDateTime.now();
    }
}