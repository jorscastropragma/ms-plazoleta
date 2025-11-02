package com.plazoleta.plazoleta.domain.spi;

public interface ISeguridadContextPort {
    boolean esPropietarioDeRestaurante(Long idRestaurante);
    boolean esPropietarioDePlato(Long idPlato);
    Long obtenerIdUsuarioAutenticado();
}
