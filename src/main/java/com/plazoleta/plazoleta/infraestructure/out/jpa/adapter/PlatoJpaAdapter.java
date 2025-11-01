package com.plazoleta.plazoleta.infraestructure.out.jpa.adapter;

import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.infraestructure.exception.MensajeInfraestructuraException;
import com.plazoleta.plazoleta.infraestructure.exception.RecursoNoEncontradoException;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.PlatoEntity;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.PlatoEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IPlatoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PlatoJpaAdapter implements IPlatoPersistencePort {

    private final IPlatoRepository platoRepository;
    private final PlatoEntityMapper platoEntityMapper;

    @Override
    public void guardarPlato(Plato plato) {
        platoRepository.save(platoEntityMapper.toPlatoEntity(plato));
    }

    @Override
    public Plato actualizarPlato(Plato plato, Long idPlato) {

        PlatoEntity platoEntity;
        try {
            platoEntity = platoRepository.getReferenceById(idPlato);
            platoEntityMapper.updatePlatoEntityFromPlato(platoEntity,plato);
        }catch (EntityNotFoundException ex){
            throw new RecursoNoEncontradoException(
                    MensajeInfraestructuraException.PLATO_NO_ENCONTRADO.getMensaje());
        }

        return platoEntityMapper.toPlato(platoRepository.save(platoEntity));

    }

    @Override
    public Page<Plato> obtenerPlatos(Pageable pageable, Long idRestaurante, Long idCategoria) {
        Page<PlatoEntity> platoEntities;
        if (idCategoria == null){
            platoEntities = platoRepository.findByIdRestauranteAndActivoTrue(idRestaurante,pageable);
        }else{
            platoEntities = platoRepository.findByIdRestauranteAndIdCategoriaAndActivoTrue(idRestaurante,idCategoria,pageable);
        }
        if (platoEntities.isEmpty()){
            throw new RecursoNoEncontradoException(MensajeInfraestructuraException.PLATOS_NO_ENCONTRADOS.getMensaje());
        }
        return platoEntityMapper.toPagePlato(platoEntities);
    }

    @Override
    public Plato obtenerPlatoPorId(Long idPlato) {
        PlatoEntity platoEntity = platoRepository.findById(idPlato).orElse(null);
        return platoEntityMapper.toPlato(platoEntity);
    }
}
