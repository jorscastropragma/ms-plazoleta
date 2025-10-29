package com.plazoleta.plazoleta.infraestructure.exception;

public class CategoriaPlatoNoEncontradoException extends RuntimeException{
    public CategoriaPlatoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
