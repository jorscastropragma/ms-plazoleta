package com.plazoleta.plazoleta.domain.exception;

public enum MensajeDomainException {
    RESTAURANTE_NO_ENCONTRADO("Restaurante no encontrado."),
    NO_ES_EL_PROPIETARIO_DEL_RESTAURANTE("No es el propietario del restaurante."),
    NO_ES_EL_PROPIETARIO_DEL_PLATO("No es el propietario del plato."),
    NOMBRE_RESTAURANTE_INVALIDO("El nombre del restaurante es invalido.");

    private String mensaje;

    MensajeDomainException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
