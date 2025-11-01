package com.plazoleta.plazoleta.infraestructure.out.jpa.repository;

import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.RestauranteEmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestauranteEmpleadoRepository extends JpaRepository<RestauranteEmpleadoEntity,Long> {
}
