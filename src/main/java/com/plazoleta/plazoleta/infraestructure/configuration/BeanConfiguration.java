package com.plazoleta.plazoleta.infraestructure.configuration;

import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.api.IRestauranteEmpleadoServicePort;
import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.spi.*;
import com.plazoleta.plazoleta.domain.usecase.PlatoUseCase;
import com.plazoleta.plazoleta.domain.usecase.RestauranteEmpleadoUseCase;
import com.plazoleta.plazoleta.domain.usecase.RestauranteUseCase;
import com.plazoleta.plazoleta.domain.validations.RestauranteValidador;
import com.plazoleta.plazoleta.infraestructure.out.jpa.adapter.CategoriaJpaAdapter;
import com.plazoleta.plazoleta.infraestructure.out.jpa.adapter.PlatoJpaAdapter;
import com.plazoleta.plazoleta.infraestructure.out.jpa.adapter.RestauranteEmpleadoJpaAdapter;
import com.plazoleta.plazoleta.infraestructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.CategoriaEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.PlatoEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.RestauranteEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.ICategoriaRepository;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IPlatoRepository;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteEmpleadoRepository;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteRepository;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.adapter.UsuarioRestConsumerAdapter;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.feign.UsuarioFeignClient;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.mapper.UsuarioDtoRestConsumerMapper;
import com.plazoleta.plazoleta.infraestructure.out.security.SecurityContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestauranteRepository iRestauranteRepository;
    private final RestauranteEntityMapper restauranteEntityMapper;
    private final UsuarioFeignClient usuarioFeignClient;
    private final UsuarioDtoRestConsumerMapper usuarioDtoRestConsumerMapper;
    private final IPlatoRepository iPlatoRepository;
    private final PlatoEntityMapper platoEntityMapper;
    private final CategoriaEntityMapper categoriaEntityMapper;
    private final ICategoriaRepository iCategoriaRepository;
    private final IRestauranteEmpleadoRepository iRestauranteEmpleadoRepository;

    @Bean
    public IUsuarioPersistencePort iUsuarioPersistencePort() {
        return new UsuarioRestConsumerAdapter(usuarioFeignClient, usuarioDtoRestConsumerMapper);
    }

    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort() {
        return new RestauranteJpaAdapter(iRestauranteRepository, restauranteEntityMapper);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {
        return new RestauranteUseCase(restaurantePersistencePort(), iUsuarioPersistencePort());
    }

    @Bean
    public IPlatoPersistencePort platoPersistencePort(){
        return new PlatoJpaAdapter(iPlatoRepository,platoEntityMapper);
    }

    public ISeguridadContextPort seguridadContextPort(){
        return new SecurityContextUtil(usuarioFeignClient,iRestauranteRepository,iPlatoRepository);
    }

    public ICategoriaPersistencePort categoriaPersistencePort(){
        return new CategoriaJpaAdapter(iCategoriaRepository,categoriaEntityMapper);
    }

    @Bean
    public IPlatoServicePort platoServicePort(){
        return new PlatoUseCase(platoPersistencePort(),restaurantePersistencePort(),
                seguridadContextPort(),categoriaPersistencePort());
    }

    @Bean
    public IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort(){
        return new RestauranteEmpleadoJpaAdapter(iRestauranteEmpleadoRepository, iRestauranteRepository);
    }

    @Bean
    public IRestauranteEmpleadoServicePort restauranteEmpleadoServicePort(){
        return new RestauranteEmpleadoUseCase(restauranteEmpleadoPersistencePort());
    }

}
