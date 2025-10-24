package com.plazoleta.plazoleta.aplication.mapper;

import com.plazoleta.plazoleta.aplication.dto.RestauranteRequest;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestauranteRequestMapper {
    Restaurante toRestaurante(RestauranteRequest restauranteRequest);
}
