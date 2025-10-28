package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.exception.NoEsPropietarioException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.ISeguridadContextPort;

public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final ISeguridadContextPort seguiridadContextPort;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort,
                        IRestaurantePersistencePort restaurantePersistencePort,
                        ISeguridadContextPort seguiridadContextPort) {
        this.platoPersistencePort = platoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.seguiridadContextPort = seguiridadContextPort;
    }

    @Override
    public void guardarPlato(Plato plato) {
        if (!restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())){
            throw new RestauranteNoEncontradoException("Restaurante no encontrado.");
        }
        if (!seguiridadContextPort.usuarioAutenticadoEsPropietario(plato.getIdRestaurante())){
            throw new NoEsPropietarioException("No es propietario del restaurante.");
        }
        plato.setActivo(true);
        platoPersistencePort.guardarPlato(plato);
    }

    @Override
    public Plato actualizarPlato(Plato plato, Long id) {
        if (!seguiridadContextPort.usuarioAutenticadoEsPropietario(plato.getIdRestaurante())){
            throw new NoEsPropietarioException("No es propietario del restaurante.");
        }
        return platoPersistencePort.actualizarPlato(plato,id);
    }
}
