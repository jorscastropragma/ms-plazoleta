package com.plazoleta.plazoleta.infraestructure.out.jpa.mapper;

import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PlatoEntityMapper {
    PlatoEntity toPlatoEntity(Plato plato);
    Plato toPlato(PlatoEntity platoEntity);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePlatoEntityFromPlato(@MappingTarget PlatoEntity platoEntity, Plato plato);
}
