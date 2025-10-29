package com.plazoleta.plazoleta.infraestructure.out.jpa.adapter;

import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.spi.ICategoriaPersistencePort;
import com.plazoleta.plazoleta.infraestructure.exception.CategoriaPlatoNoEncontradoException;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.CategoriaEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.ICategoriaRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriaJpaAdapter implements ICategoriaPersistencePort {

    private final ICategoriaRepository categoriaRepository;
    private final CategoriaEntityMapper categoriaEntityMapper;

    @Override
    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaEntityMapper.toCategoria(categoriaRepository.findById(id).orElseThrow(() ->
                new CategoriaPlatoNoEncontradoException("No se encontro categoria con id: " + id)));
    }
}
