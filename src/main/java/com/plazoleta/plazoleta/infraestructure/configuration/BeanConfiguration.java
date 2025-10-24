package com.plazoleta.plazoleta.infraestructure.configuration;

import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.plazoleta.domain.usecase.RestauranteUseCase;
import com.plazoleta.plazoleta.domain.validations.IRestauranteValidador;
import com.plazoleta.plazoleta.domain.validations.RestauranteValidador;
import com.plazoleta.plazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.RestauranteEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteRepository;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.mapper.UsuarioDtoRestConsumerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestauranteRepository iRestauranteRepository;
    private final RestauranteEntityMapper restauranteEntityMapper;
    private final RestTemplate restTemplate;
    private final UsuarioDtoRestConsumerMapper usuarioDtoRestConsumerMapper;
    private final IUsuarioPersistencePort iUsuarioPersistencePort;



    @Bean
    public IRestauranteValidador  restauranteValidador() {
        return new RestauranteValidador(iUsuarioPersistencePort);
    }

    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort() {
        return new RestauranteJpaAdapter(iRestauranteRepository, restauranteEntityMapper);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {
        return new RestauranteUseCase(restaurantePersistencePort(), restauranteValidador());
    }
}
