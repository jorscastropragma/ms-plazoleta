package com.plazoleta.plazoleta.infraestructure.out.jpa.mapper;

import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.CategoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CategoriaEntityMapper {
    Categoria toCategoria(CategoriaEntity categoriaEntity);
}
