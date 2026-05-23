package br.com.fiap.calorias.dto;

import br.com.fiap.calorias.model.Usuario;
import br.com.fiap.calorias.model.UsuarioRole;

//record - lida com dados em si tratando como se fosse final
//         sem a necessidade de getters e setters
public record UsuarioExibicaoDTO(
        Long usuarioId,
        String nome,
        String email,
        UsuarioRole role
) {
    public UsuarioExibicaoDTO(Usuario usuario) {
        this(
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }
}

//dto - lida com a transferencia de dados da api - controla o fluxo de dados que vão e voltam