package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPlatoHandler {
    void guardarPlato(PlatoRequest platoRequest);
    PlatoResponse actualizarPlato(PlatoPrecioDescripcionRequest platoRequest, Long idPlato);
    PlatoResponse cambiarEstadoPlato(PlatoEstadoRequest platoRequest, Long idPlato);
    Page<PlatoCategoriaResponse> obtenerPlatos(Pageable pageable, Long idRestaurante, Long idCategoria);
}
