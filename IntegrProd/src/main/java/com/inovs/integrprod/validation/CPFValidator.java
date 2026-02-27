package com.inovs.integrprod.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    @Override
    public void initialize(ValidCPF constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null) {
            return false;
        }

        // Remove caracteres não numéricos
        String cpfNumerico = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem 11 dígitos
        if (cpfNumerico.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (inválido)
        if (cpfNumerico.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula primeiro dígito verificador
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Integer.parseInt(cpfNumerico.substring(i, i + 1)) * (10 - i);
            }
            int digito1 = 11 - (soma % 11);
            digito1 = digito1 > 9 ? 0 : digito1;

            // Calcula segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Integer.parseInt(cpfNumerico.substring(i, i + 1)) * (11 - i);
            }
            int digito2 = 11 - (soma % 11);
            digito2 = digito2 > 9 ? 0 : digito2;

            // Verifica se os dígitos calculados conferem com os informados
            return Integer.parseInt(cpfNumerico.substring(9, 10)) == digito1 &&
                    Integer.parseInt(cpfNumerico.substring(10, 11)) == digito2;
        } catch (Exception e) {
            return false;
        }
    }
}