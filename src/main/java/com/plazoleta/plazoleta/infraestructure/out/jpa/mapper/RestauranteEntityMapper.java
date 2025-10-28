package com.plazoleta.plazoleta.infraestructure.out.jpa.mapper;

import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestauranteEntityMapper {
    RestauranteEntity  toRestauranteEntity(Restaurante restaurante);

    Restaurante toRestaurante(RestauranteEntity restauranteEntity);

    default Page<Restaurante> toPageRestaurante(Page<RestauranteEntity> page){
        return page.map(this::toRestaurante);
    }
}
