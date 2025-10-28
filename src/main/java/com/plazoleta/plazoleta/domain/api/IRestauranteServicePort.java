package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.Restaurante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IRestauranteServicePort {
    void guardarRestaurante(Restaurante restaurante);
    Page<Restaurante> obtenerRestaurantes(Pageable pageable);
}
