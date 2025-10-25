package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;

public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort,
                        IRestaurantePersistencePort restaurantePersistencePort) {
        this.platoPersistencePort = platoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
    }

    @Override
    public void guardarPlato(Plato plato) {
        if (!restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())){
            throw new RestauranteNoEncontradoException("Restaurante no encontrado.");
        }
        plato.setActivo(true);
        platoPersistencePort.guardarPlato(plato);
    }

    @Override
    public Plato actualizarPlato(Plato plato, Long id) {
        return platoPersistencePort.actualizarPlato(plato,id);
    }
}
