package com.plazoleta.plazoleta.infraestructure.out.jpa.adapter;

import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.infraestructure.exception.MensajeInfraestructuraException;
import com.plazoleta.plazoleta.infraestructure.exception.RecursoNoEncontradoException;
import com.plazoleta.plazoleta.infraestructure.exception.RestriccionRecursoYaExisteException;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.RestauranteEntity;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.RestauranteEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IRestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class RestauranteJpaAdapter implements IRestaurantePersistencePort {

    private final IRestauranteRepository restauranteRepository;

    private final RestauranteEntityMapper restauranteEntityMapper;

    @Override
    public void guardarRestaurante(Restaurante restaurante) {
        if (restauranteRepository.existsByNit(restaurante.getNit())){
            throw new RestriccionRecursoYaExisteException(
                    MensajeInfraestructuraException.NIT_RESTAURANTE_YA_EXISTE.getMensaje());
        }
        restauranteRepository.save(restauranteEntityMapper.toRestauranteEntity(restaurante));
    }

    @Override
    public boolean existeRestaurantePorId(Long idRestaurante) {
        return restauranteRepository.existsById(idRestaurante);
    }

    @Override
    public Page<Restaurante> obtenerRestaurantes(Pageable pageable) {
        Page<RestauranteEntity> restaurantes = restauranteRepository.findAll(pageable);
        if (restaurantes.isEmpty()){
            throw new RecursoNoEncontradoException(MensajeInfraestructuraException.RESTAURANTES_NO_ENCONTRADOS.getMensaje());
        }
        return restauranteEntityMapper.toPageRestaurante(restaurantes);
    }
}
