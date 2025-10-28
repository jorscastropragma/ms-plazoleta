package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.RestauranteListResponse;
import com.plazoleta.plazoleta.aplication.dto.RestauranteRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestauranteHandler {
    void guardarRestaurante(RestauranteRequest restauranteRequest);
    Page<RestauranteListResponse> obtenerRestaurantes(Pageable pageable);
}
