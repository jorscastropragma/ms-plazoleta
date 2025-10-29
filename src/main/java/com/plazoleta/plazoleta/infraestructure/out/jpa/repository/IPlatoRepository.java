package com.plazoleta.plazoleta.infraestructure.out.jpa.repository;

import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlatoRepository extends JpaRepository<PlatoEntity,Long> {
    Page<PlatoEntity> findByIdRestauranteAndIdCategoriaAndActivoTrue(Long idRestaurante, Long idCategoria, Pageable pageable);
    Page<PlatoEntity> findByIdRestauranteAndActivoTrue(Long idRestaurante, Pageable pageable);
}
