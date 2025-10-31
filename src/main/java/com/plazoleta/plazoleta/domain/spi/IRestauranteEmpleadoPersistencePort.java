package com.plazoleta.plazoleta.domain.spi;

public interface IRestauranteEmpleadoPersistencePort {
    void guardarRestauranteEmpleado(Long idRestaurante, Long idEmpleado);
}
