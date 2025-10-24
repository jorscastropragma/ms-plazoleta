package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.RestauranteRequest;
import com.plazoleta.plazoleta.aplication.mapper.RestauranteRequestMapper;
import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteHandler implements IRestauranteHandler{

    private final RestauranteRequestMapper restauranteRequestMapper;

    private final IRestauranteServicePort restauranteServicePort;

    @Override
    public void guardarRestaurante(RestauranteRequest restauranteRequest) {
        restauranteServicePort.guardarRestaurante(restauranteRequestMapper.toRestaurante(restauranteRequest));
    }
}
