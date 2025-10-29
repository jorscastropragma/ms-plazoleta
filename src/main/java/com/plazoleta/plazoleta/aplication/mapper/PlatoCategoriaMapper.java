package com.plazoleta.plazoleta.aplication.mapper;

import com.plazoleta.plazoleta.aplication.dto.PlatoCategoriaResponse;
import com.plazoleta.plazoleta.domain.model.Plato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PlatoCategoriaMapper {
    PlatoCategoriaResponse toPlatoCategoria(Plato plato);

    default Page<PlatoCategoriaResponse> toPagePlatoCategoriaResponse(Page<Plato> page){
        return page.map(this::toPlatoCategoria);
    }
}
