package com.plazoleta.plazoleta.infraestructure.out.restconsumer.adapter;

import com.plazoleta.plazoleta.domain.model.Usuario;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.plazoleta.infraestructure.exception.UsuarioNoEncontradoException;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.dto.UsuarioRequest;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.mapper.UsuarioDtoRestConsumerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class UsuarioRestConsumerAdapter implements IUsuarioPersistencePort {

    private final RestTemplate restTemplate;

    private final UsuarioDtoRestConsumerMapper usuarioDtoRestConsumerMapper;

    @Override
    public Usuario obtenerUsuarioPorId(Long idUsuario) {

        try {
            String url = "/{idUsuario}";
            ResponseEntity<UsuarioRequest> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    UsuarioRequest.class,
                    idUsuario
            );
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return usuarioDtoRestConsumerMapper.toUsuario(response.getBody());
            }
        }catch (HttpClientErrorException | ResourceAccessException ex) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado: " + idUsuario);
        }
        return null;
    }
}
