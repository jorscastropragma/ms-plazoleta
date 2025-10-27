package com.plazoleta.plazoleta.infraestructure.out.restconsumer.adapter;

import com.plazoleta.plazoleta.domain.model.Usuario;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.feign.UsuarioFeignClient;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.mapper.UsuarioDtoRestConsumerMapper;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UsuarioRestConsumerAdapter implements IUsuarioPersistencePort {

    private final UsuarioFeignClient usuarioFeignClient;

    private final UsuarioDtoRestConsumerMapper usuarioDtoRestConsumerMapper;

    @Override
    public Usuario obtenerUsuarioPorId(Long idUsuario) {
        return usuarioDtoRestConsumerMapper.toUsuario(usuarioFeignClient.obtenerUsuarioPorId(idUsuario));
    }
}
