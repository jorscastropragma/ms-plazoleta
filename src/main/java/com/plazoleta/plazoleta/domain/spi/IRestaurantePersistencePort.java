package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Restaurante;

public interface IRestaurantePersistencePort {
    void guardarRestaurante(Restaurante restaurante);
}
