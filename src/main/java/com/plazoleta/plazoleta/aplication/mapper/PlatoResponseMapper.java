package com.plazoleta.plazoleta.aplication.mapper;

import com.plazoleta.plazoleta.aplication.dto.PlatoResponse;
import com.plazoleta.plazoleta.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PlatoResponseMapper {
    PlatoResponse toPlatoResponse(Plato plato);
}
