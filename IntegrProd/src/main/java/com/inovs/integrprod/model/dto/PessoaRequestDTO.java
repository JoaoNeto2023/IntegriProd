package com.inovs.integrprod.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaRequestDTO {

    @NotBlank(message = "Tipo de pessoa é obrigatório")
    @Pattern(regexp = "PF|PJ", message = "Tipo de pessoa deve ser PF ou PJ")
    private String tipoPessoa;

    @NotBlank(message = "Nome/Razão social é obrigatório")
    @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    private String nomeRazao;

    @Size(max = 200, message = "Nome fantasia deve ter no máximo 200 caracteres")
    private String nomeFantasia;

    @NotBlank(message = "CPF/CNPJ é obrigatório")
    private String cpfCnpj;

    private String rgIe;

    private LocalDate dataNascimento;

    private String sexo;

    private String estadoCivil;

    private String profissao;

    @Email(message = "Email inválido")
    private String email;

    private String telefone1;

    private String telefone2;

    private String celular;

    private String site;

    private String observacoes;

    private String situacao = "ATIVO";
}