package com.plazoleta.plazoleta.infraestructure.out.security;


import com.plazoleta.plazoleta.domain.spi.ISeguridadContextPort;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteRepository;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.dto.UsuarioInfo;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.feign.UsuarioFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class SecurityContextUtil implements ISeguridadContextPort {

    private final UsuarioFeignClient usuarioFeignClient;
    private final IRestauranteRepository restauranteRepository;

    @Override
    public boolean usuarioAutenticadoEsPropietario(Long idRestaurante) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String correoAutenticado = authentication.getName();
        RestauranteEntity restaurante = restauranteRepository.getReferenceById(idRestaurante);
        UsuarioInfo usuario = usuarioFeignClient.obtenerUsuarioPorId(restaurante.getIdUsuario());

        return usuario.getCorreo().equals(correoAutenticado);
    }
}
