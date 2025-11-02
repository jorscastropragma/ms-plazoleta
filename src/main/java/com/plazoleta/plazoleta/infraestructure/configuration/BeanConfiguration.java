package com.plazoleta.plazoleta.infraestructure.configuration;

import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.api.IRestauranteEmpleadoServicePort;
import com.plazoleta.plazoleta.domain.api.IRestauranteServicePort;
import com.plazoleta.plazoleta.domain.spi.*;
import com.plazoleta.plazoleta.domain.usecase.PedidoUseCase;
import com.plazoleta.plazoleta.domain.usecase.PlatoUseCase;
import com.plazoleta.plazoleta.domain.usecase.RestauranteEmpleadoUseCase;
import com.plazoleta.plazoleta.domain.usecase.RestauranteUseCase;
import com.plazoleta.plazoleta.domain.validations.PedidoValidador;
import com.plazoleta.plazoleta.infraestructure.out.jpa.adapter.*;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.CategoriaEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.PedidoEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.PlatoEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.RestauranteEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.*;
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
    private final IPedidoRepository pedidoRepository;
    private final PedidoEntityMapper pedidoEntityMapper;

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

    public IPedidoPersistencePort pedidoPersistencePort(){
        return new PedidoJpaAdapter(pedidoRepository,pedidoEntityMapper);
    }

    @Bean
    public IPedidoServicePort pedidoServicePort(){
        return new PedidoUseCase(pedidoPersistencePort(),
                restaurantePersistencePort(),
                platoPersistencePort(),
                new PedidoValidador(),
                restauranteEmpleadoPersistencePort(),
                seguridadContextPort()
        );
    }

}
