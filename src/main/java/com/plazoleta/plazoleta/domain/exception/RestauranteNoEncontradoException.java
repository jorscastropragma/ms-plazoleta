package com.plazoleta.plazoleta.domain.exception;

public class RestauranteNoEncontradoException extends RuntimeException{
    public RestauranteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
