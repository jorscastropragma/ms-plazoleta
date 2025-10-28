package com.plazoleta.plazoleta.domain.spi;

public interface ISeguridadContextPort {
    boolean usuarioAutenticadoEsPropietario(Long idRestaurante);
}
