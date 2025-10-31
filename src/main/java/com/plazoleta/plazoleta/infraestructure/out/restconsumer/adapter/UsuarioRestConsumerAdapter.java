package com.plazoleta.plazoleta.infraestructure.out.restconsumer.adapter;

import com.plazoleta.plazoleta.domain.model.Usuario;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.plazoleta.infraestructure.exception.MensajeInfraestructuraException;
import com.plazoleta.plazoleta.infraestructure.exception.RecursoNoEncontradoException;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.dto.UsuarioInfo;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.feign.UsuarioFeignClient;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.mapper.UsuarioDtoRestConsumerMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UsuarioRestConsumerAdapter implements IUsuarioPersistencePort {

    private final UsuarioFeignClient usuarioFeignClient;

    private final UsuarioDtoRestConsumerMapper usuarioDtoRestConsumerMapper;

    @Override
    public Usuario obtenerUsuarioPorId(Long idUsuario) {
        UsuarioInfo usuarioInfo;
        try {
            usuarioInfo = usuarioFeignClient.obtenerUsuarioPorId(idUsuario);
        }catch (FeignException ex){
            throw new RecursoNoEncontradoException(
                    MensajeInfraestructuraException.USUARIO_NO_ENCONTRADO.getMensaje());
        }
        return usuarioDtoRestConsumerMapper.toUsuario(usuarioInfo);
    }
}
