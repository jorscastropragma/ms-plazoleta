package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.plazoleta.domain.validations.RestauranteValidador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class RestauranteUseCase implements IRestauranteServicePort {

    private final IRestaurantePersistencePort restaurantePersistencePort;

    private RestauranteValidador restauranteValidador;


    public RestauranteUseCase(IRestaurantePersistencePort restaurantePersistencePort,
                              IUsuarioPersistencePort usuarioPersistencePort) {
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.restauranteValidador = new RestauranteValidador(usuarioPersistencePort);
    }

    @Override
    public void guardarRestaurante(Restaurante restaurante) {
        restauranteValidador.validarNombreRestaurante(restaurante.getNombre());
        restauranteValidador.validarPropietarioRestaurante(restaurante.getIdUsuario());
        restaurantePersistencePort.guardarRestaurante(restaurante);
    }

    @Override
    public Page<Restaurante> obtenerRestaurantes(Pageable pageable) {
        return restaurantePersistencePort.obtenerRestaurantes(pageable);
    }
}
