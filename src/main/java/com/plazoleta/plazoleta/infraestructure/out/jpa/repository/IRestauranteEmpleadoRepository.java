package com.plazoleta.plazoleta.infraestructure.out.jpa.repository;

import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.RestauranteEmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRestauranteEmpleadoRepository extends JpaRepository<RestauranteEmpleadoEntity,Long> {
    Optional<RestauranteEmpleadoEntity> findByIdEmpleado(Long idEmpleado);
}
