package com.plazoleta.plazoleta.infraestructure.exception;

public class UsuarioNoAutenticadoException extends RuntimeException {
    public UsuarioNoAutenticadoException(String mensaje) {
        super(mensaje);
    }
}
