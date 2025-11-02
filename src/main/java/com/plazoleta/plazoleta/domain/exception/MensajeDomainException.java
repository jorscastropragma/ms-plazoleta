package com.plazoleta.plazoleta.domain.exception;

public enum MensajeDomainException {
    RESTAURANTE_NO_ENCONTRADO("Restaurante no encontrado."),
    PLATO_NO_ENCONTRADO("Plato no encontrado."),
    PLATO_DIFERENTE_RESTAURANTE("Todos los platos deben ser del mismo restaurante"),
    NO_ES_EL_PROPIETARIO_DEL_RESTAURANTE("No es el propietario del restaurante."),
    NO_ES_EL_PROPIETARIO_DEL_PLATO("No es el propietario del plato."),
    NOMBRE_RESTAURANTE_INVALIDO("El nombre del restaurante es invalido."),
    PEDIDO_DIFERENTE_PENDIENTE("El pedido debe estar en estado PENDIENTE"),
    PEDIDO_EXISTE("Ya existe un pedido activo para este cliente");

    private String mensaje;

    MensajeDomainException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
