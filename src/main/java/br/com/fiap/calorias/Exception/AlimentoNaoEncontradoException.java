package br.com.fiap.calorias.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlimentoNaoEncontradoException extends RuntimeException {
    public AlimentoNaoEncontradoException(String message) {
        super(message);
    }
}
