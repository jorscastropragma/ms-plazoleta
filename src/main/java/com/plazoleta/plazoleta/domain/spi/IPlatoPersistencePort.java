package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Plato;

public interface IPlatoPersistencePort {
    void guardarPlato(Plato plato);
}
