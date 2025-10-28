package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantePersistencePort {
    void guardarRestaurante(Restaurante restaurante);
    boolean existeRestaurantePorId(Long idRestaurante);
    Page<Restaurante> obtenerRestaurantes(Pageable pageable);
}
