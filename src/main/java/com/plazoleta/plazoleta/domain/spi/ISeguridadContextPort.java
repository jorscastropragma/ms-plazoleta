package com.plazoleta.plazoleta.domain.spi;

public interface ISeguridadContextPort {
    boolean esPropietarioDeRestaurante(Long idRestaurante, String emailUsuario);
    boolean esPropietarioDePlato(Long idPlato, String emailUsuario);
}
