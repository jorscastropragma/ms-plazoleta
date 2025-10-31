package com.plazoleta.plazoleta.infraestructure.exception;

public class RestriccionRecursoYaExisteException extends RuntimeException{
    public RestriccionRecursoYaExisteException(String mensaje) {
        super(mensaje);
    }
}
