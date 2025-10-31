package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.RestauranteEmpleadoRequest;
import com.plazoleta.plazoleta.domain.api.IRestauranteEmpleadoServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteEmpleadoHandler implements IRestauranteEmpleadoHandler{

    private final IRestauranteEmpleadoServicePort restauranteEmpleadoServicePort;

    @Override
    public void guardarRestauranteEmpleado(RestauranteEmpleadoRequest request) {
        restauranteEmpleadoServicePort.guardarRestauranteEmpleado(request.getIdRestaurante(), request.getIdEmpleado());
    }
}
