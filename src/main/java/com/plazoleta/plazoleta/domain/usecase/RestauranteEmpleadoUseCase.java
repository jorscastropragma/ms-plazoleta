package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IRestauranteEmpleadoServicePort;
import com.plazoleta.plazoleta.domain.spi.IRestauranteEmpleadoPersistencePort;

public class RestauranteEmpleadoUseCase implements IRestauranteEmpleadoServicePort {

    private final IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort;

    public RestauranteEmpleadoUseCase(IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort) {
        this.restauranteEmpleadoPersistencePort = restauranteEmpleadoPersistencePort;
    }

    @Override
    public void guardarRestauranteEmpleado(Long idRestaurante, Long idEmpleado) {
        restauranteEmpleadoPersistencePort.guardarRestauranteEmpleado(idRestaurante, idEmpleado);
    }

}
