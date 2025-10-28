package com.plazoleta.plazoleta.aplication.mapper;

import com.plazoleta.plazoleta.aplication.dto.RestauranteListResponse;
import com.plazoleta.plazoleta.aplication.dto.RestauranteRequest;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestauranteRequestMapper {
    Restaurante toRestaurante(RestauranteRequest restauranteRequest);

    RestauranteListResponse toRestauranteResponse(Restaurante restaurante);

    default Page<RestauranteListResponse> toPageRestauranteResponse(Page<Restaurante> page){
        return page.map(this::toRestauranteResponse);
    }

}
