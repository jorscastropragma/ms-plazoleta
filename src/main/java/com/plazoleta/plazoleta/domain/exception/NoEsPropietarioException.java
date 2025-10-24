package com.plazoleta.plazoleta.domain.exception;

public class NoEsPropietarioException extends RuntimeException {
    public NoEsPropietarioException(String mensaje) {
        super(mensaje);
    }
}
