package com.plazoleta.plazoleta.infraestructure.configuration;

import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.usecase.RestauranteUseCase;
import com.plazoleta.plazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.RestauranteEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestauranteRepository iRestauranteRepository;
    private final RestauranteEntityMapper restauranteEntityMapper;

    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort() {
        return new RestauranteJpaAdapter(iRestauranteRepository, restauranteEntityMapper);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {
        return new RestauranteUseCase(restaurantePersistencePort());
    }
}
