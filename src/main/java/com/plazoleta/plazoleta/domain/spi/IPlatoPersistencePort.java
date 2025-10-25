package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Plato;

public interface IPlatoPersistencePort {
    void guardarPlato(Plato plato);
    Plato actualizarPlato(Plato plato, Long idPlato);
}
