package com.plazoleta.plazoleta.infraestructure.out.security;

import com.plazoleta.plazoleta.domain.spi.ISeguridadContextPort;
import com.plazoleta.plazoleta.infraestructure.exception.MensajeInfraestructuraException;
import com.plazoleta.plazoleta.infraestructure.exception.RecursoNoEncontradoException;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IPlatoRepository;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteRepository;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.dto.UsuarioInfo;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.feign.UsuarioFeignClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityContextUtil implements ISeguridadContextPort {

    private final UsuarioFeignClient usuarioFeignClient;
    private final IRestauranteRepository restauranteRepository;
    private final IPlatoRepository platoRepository;

    @Override
    public boolean esPropietarioDeRestaurante(Long idRestaurante, String emailUsuario) {

        RestauranteEntity restaurante = restauranteRepository.getReferenceById(idRestaurante);
        UsuarioInfo usuario = usuarioFeignClient.obtenerUsuarioPorId(restaurante.getIdUsuario());

        return usuario.getCorreo().equals(emailUsuario);
    }

    @Override
    public boolean esPropietarioDePlato(Long idPlato, String emailUsuario) {

        PlatoEntity plato = platoRepository.getReferenceById(idPlato);
        RestauranteEntity restaurante = restauranteRepository.getReferenceById(plato.getIdRestaurante());
        UsuarioInfo usuario = usuarioFeignClient.obtenerUsuarioPorId(restaurante.getIdUsuario());

        return usuario.getCorreo().equals(emailUsuario);
    }
}
