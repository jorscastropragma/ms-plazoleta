package com.plazoleta.plazoleta.infraestructure.out.jpa.repository;

import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.RestauranteEmpleadoEntity;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestauranteEmpleadoRepository extends JpaRepository<RestauranteEmpleadoEntity,Long> {
}
