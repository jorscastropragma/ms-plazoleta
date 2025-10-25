package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.PlatoRequest;
import com.plazoleta.plazoleta.domain.model.Plato;

public interface IPlatoHandler {
    void guardarPlato(PlatoRequest platoRequest);
}
