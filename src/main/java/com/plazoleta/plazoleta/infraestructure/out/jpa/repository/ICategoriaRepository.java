package com.plazoleta.plazoleta.infraestructure.out.jpa.repository;

import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository<CategoriaEntity,Long> {
}
