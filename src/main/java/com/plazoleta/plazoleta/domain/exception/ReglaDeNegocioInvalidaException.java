package com.plazoleta.plazoleta.domain.exception;

public class ReglaDeNegocioInvalidaException extends RuntimeException{
    public ReglaDeNegocioInvalidaException(String mensaje) {
       super(mensaje);
    }
}
