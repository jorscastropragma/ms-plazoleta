package com.plazoleta.plazoleta.infraestructure.out.restconsumer.mapper;

import com.plazoleta.plazoleta.domain.model.Usuario;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.dto.UsuarioRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UsuarioDtoRestConsumerMapper {
    Usuario toUsuario(UsuarioRequest usuarioRequest);
}
