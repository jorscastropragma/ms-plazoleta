package com.plazoleta.plazoleta.aplication.mapper;

import com.plazoleta.plazoleta.aplication.dto.PlatoPrecioDescripcionRequest;
import com.plazoleta.plazoleta.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PlatoPrecioDescripcionRequestMapper {
    Plato toPlato(PlatoPrecioDescripcionRequest platoPrecioDescripcionRequest);
}
