package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Categoria;

public interface ICategoriaPersistencePort {
    Categoria obtenerCategoriaPorId(Long id);
}
