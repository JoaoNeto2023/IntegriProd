package com.inovs.integrprod.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<ValidCNPJ, String> {

    @Override
    public void initialize(ValidCNPJ constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null) {
            return false;
        }

        // Remove caracteres não numéricos
        String cnpjNumerico = cnpj.replaceAll("[^0-9]", "");

        // Verifica se tem 14 dígitos
        if (cnpjNumerico.length() != 14) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (inválido)
        if (cnpjNumerico.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Calcula primeiro dígito verificador
        try {
            int soma = 0;
            int peso = 2;
            for (int i = 11; i >= 0; i--) {
                int num = Integer.parseInt(cnpjNumerico.substring(i, i + 1));
                soma += num * peso;
                peso = peso == 9 ? 2 : peso + 1;
            }
            int digito1 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            // Calcula segundo dígito verificador
            soma = 0;
            peso = 2;
            for (int i = 12; i >= 0; i--) {
                int num = Integer.parseInt(cnpjNumerico.substring(i, i + 1));
                soma += num * peso;
                peso = peso == 9 ? 2 : peso + 1;
            }
            int digito2 = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            // Verifica se os dígitos calculados conferem com os informados
            return Integer.parseInt(cnpjNumerico.substring(12, 13)) == digito1 &&
                    Integer.parseInt(cnpjNumerico.substring(13, 14)) == digito2;
        } catch (Exception e) {
            return false;
        }
    }
}