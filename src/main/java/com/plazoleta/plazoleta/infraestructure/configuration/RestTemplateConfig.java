package com.plazoleta.plazoleta.infraestructure.configuration;

import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.adapter.UsuarioRestConsumerAdapter;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.mapper.UsuarioDtoRestConsumerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    @Value("${usuario.url}")
    private String usuarioUrl;

    private final UsuarioDtoRestConsumerMapper usuarioDtoRestConsumerMapper;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .rootUri(usuarioUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public IUsuarioPersistencePort getIUsuarioPersistencePort() {
        return new UsuarioRestConsumerAdapter(restTemplate(new RestTemplateBuilder()), usuarioDtoRestConsumerMapper);
    }

}
