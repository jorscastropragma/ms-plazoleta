package com.plazoleta.plazoleta.infraestructure.exception;

public enum MensajeInfraestructuraException {
    NIT_RESTAURANTE_YA_EXISTE("El NIT ya existe."),
    RESTAURANTES_NO_ENCONTRADOS("No hay restaurantes registrados."),
    PLATO_NO_ENCONTRADO("El plato no existe"),
    PLATOS_NO_ENCONTRADOS("No hay platos registrados."),
    CATEGORIA_NO_ENCONTRADA("No se encontro categoria"),
    USUARIO_NO_ENCONTRADO("El propietario no existe."),
    PEDIDO_NO_ENCONTRADO("El pedido no existe."),
    USUARIO_NO_ENCONTRADO_AUTENTICADO("No se puede obtener el id del usuario autenticado");

    private String mensaje;

    MensajeInfraestructuraException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
