package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.PlatoPrecioDescripcionRequest;
import com.plazoleta.plazoleta.aplication.dto.PlatoRequest;
import com.plazoleta.plazoleta.aplication.dto.PlatoResponse;
import com.plazoleta.plazoleta.domain.model.Plato;

public interface IPlatoHandler {
    void guardarPlato(PlatoRequest platoRequest);
    PlatoResponse actualizarPlato(PlatoPrecioDescripcionRequest platoRequest, Long idPlato);
}
