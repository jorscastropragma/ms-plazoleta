package com.plazoleta.plazoleta.domain.exception;

public class NombreRestauranteInvalidoException extends RuntimeException {
    public NombreRestauranteInvalidoException(String mensaje) {
        super(mensaje);
    }
}
