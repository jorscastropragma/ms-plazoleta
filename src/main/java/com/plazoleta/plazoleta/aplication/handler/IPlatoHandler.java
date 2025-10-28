package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.PlatoEstadoRequest;
import com.plazoleta.plazoleta.aplication.dto.PlatoPrecioDescripcionRequest;
import com.plazoleta.plazoleta.aplication.dto.PlatoRequest;
import com.plazoleta.plazoleta.aplication.dto.PlatoResponse;

public interface IPlatoHandler {
    void guardarPlato(PlatoRequest platoRequest);
    PlatoResponse actualizarPlato(PlatoPrecioDescripcionRequest platoRequest, Long idPlato);
    PlatoResponse cambiarEstadoPlato(PlatoEstadoRequest platoRequest, Long idPlato);
}
