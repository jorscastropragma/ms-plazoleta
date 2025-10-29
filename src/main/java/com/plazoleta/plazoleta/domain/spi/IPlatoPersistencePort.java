package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Plato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPlatoPersistencePort {
    void guardarPlato(Plato plato);
    Plato actualizarPlato(Plato plato, Long idPlato);
    Page<Plato> obtenerPlatos(Pageable pageable, Long idRestaurante, Long idCategoria);
}
