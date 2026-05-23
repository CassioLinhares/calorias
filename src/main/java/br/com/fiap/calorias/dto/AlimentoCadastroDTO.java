package br.com.fiap.calorias.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlimentoCadastroDTO(
        Long alimentoId,

        @NotBlank(message = "O nome do alimento é obrigatório!")
        String nome,

        @NotBlank(message = "A porção do alimento é obrigatória!")
        String porcao,

        @NotNull(message = "Informe um valor válido!")
        Double quantidadeProteina,

        @NotNull(message = "Informe um valor válido!")
        Double quantidadeCarboidrato,

        @NotNull(message = "Informe um valor válido!")
        Double quantidadeGorduras
) {
}
