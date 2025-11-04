package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPlatoHandler {
    void guardarPlato(PlatoRequest platoRequest, String emailUsuario);
    PlatoResponse actualizarPlato(PlatoPrecioDescripcionRequest platoRequest, Long idPlato, String emailUsuario);
    PlatoResponse cambiarEstadoPlato(PlatoEstadoRequest platoRequest, Long idPlato, String emailUsuario);
    Page<PlatoCategoriaResponse> obtenerPlatos(Pageable pageable, Long idRestaurante, Long idCategoria);
}
