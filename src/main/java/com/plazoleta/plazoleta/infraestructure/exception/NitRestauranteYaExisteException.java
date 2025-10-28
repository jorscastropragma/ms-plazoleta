package com.plazoleta.plazoleta.infraestructure.exception;

public class NitRestauranteYaExisteException extends RuntimeException{
    public NitRestauranteYaExisteException(String mensaje) {
        super(mensaje);
    }
}
