package com.plazoleta.plazoleta.infraestructure.configuration;

import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.plazoleta.domain.usecase.PlatoUseCase;
import com.plazoleta.plazoleta.domain.usecase.RestauranteUseCase;
import com.plazoleta.plazoleta.domain.validations.IRestauranteValidador;
import com.plazoleta.plazoleta.domain.validations.RestauranteValidador;
import com.plazoleta.plazoleta.infraestructure.out.jpa.adapter.PlatoJpaAdapter;
import com.plazoleta.plazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.PlatoEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.RestauranteEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IPlatoRepository;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteRepository;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.adapter.UsuarioRestConsumerAdapter;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.feign.UsuarioFeignClient;
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
    private final UsuarioFeignClient usuarioFeignClient;
    private final UsuarioDtoRestConsumerMapper usuarioDtoRestConsumerMapper;
    private final IPlatoRepository iPlatoRepository;
    private final PlatoEntityMapper platoEntityMapper;

    @Bean
    public IUsuarioPersistencePort iUsuarioPersistencePort() {
        return new UsuarioRestConsumerAdapter(usuarioFeignClient, usuarioDtoRestConsumerMapper);
    }

    @Bean
    public IRestauranteValidador  restauranteValidador() {
        return new RestauranteValidador(iUsuarioPersistencePort());
    }

    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort() {
        return new RestauranteJpaAdapter(iRestauranteRepository, restauranteEntityMapper);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {
        return new RestauranteUseCase(restaurantePersistencePort(), restauranteValidador());
    }

    @Bean
    public IPlatoPersistencePort platoPersistencePort(){
        return new PlatoJpaAdapter(iPlatoRepository,platoEntityMapper);
    }

    @Bean
    public IPlatoServicePort platoServicePort(){
        return new PlatoUseCase(platoPersistencePort(),restaurantePersistencePort());
    }
}
