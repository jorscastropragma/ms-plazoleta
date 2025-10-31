package com.plazoleta.plazoleta.infraestructure.out.jpa.adapter;

import com.plazoleta.plazoleta.domain.spi.IRestauranteEmpleadoPersistencePort;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.RestauranteEmpleadoEntity;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteEmpleadoRepository;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestauranteEmpleadoJpaAdapter implements IRestauranteEmpleadoPersistencePort {

    private final IRestauranteEmpleadoRepository  restauranteEmpleadoRepository;
    private final IRestauranteRepository restauranteRepository;


    @Override
    public void guardarRestauranteEmpleado(Long idRestaurante, Long idEmpleado) {
        if (!restauranteRepository.existsById(idRestaurante)){
            throw new RuntimeException("El restaurante no existe");
        }
        RestauranteEmpleadoEntity restauranteEmpleadoEntity = new RestauranteEmpleadoEntity();
        restauranteEmpleadoEntity.setIdRestaurante(idRestaurante);
        restauranteEmpleadoEntity.setIdEmpleado(idEmpleado);
        restauranteEmpleadoRepository.save(restauranteEmpleadoEntity);
    }
}
