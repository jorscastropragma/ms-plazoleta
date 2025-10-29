package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.exception.NoEsPropietarioException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.ICategoriaPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.ISeguridadContextPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PlatoUseCase implements IPlatoServicePort {

    private final IPlatoPersistencePort platoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final ISeguridadContextPort seguiridadContextPort;
    private final ICategoriaPersistencePort categoriaPersistencePort;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort,
                        IRestaurantePersistencePort restaurantePersistencePort,
                        ISeguridadContextPort seguiridadContextPort,
                        ICategoriaPersistencePort categoriaPersistencePort) {
        this.platoPersistencePort = platoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.seguiridadContextPort = seguiridadContextPort;
        this.categoriaPersistencePort = categoriaPersistencePort;
    }

    @Override
    public void guardarPlato(Plato plato) {
        if (!restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())){
            throw new RestauranteNoEncontradoException("Restaurante no encontrado.");
        }
        if (!seguiridadContextPort.esPropietarioDeRestaurante(plato.getIdRestaurante())){
            throw new NoEsPropietarioException("No es propietario del restaurante.");
        }
        plato.setActivo(true);
        platoPersistencePort.guardarPlato(plato);
    }

    @Override
    public Plato actualizarPlato(Plato plato, Long id) {
        if (!seguiridadContextPort.esPropietarioDePlato(id)){
            throw new NoEsPropietarioException("No es propietario del plato.");
        }
        return platoPersistencePort.actualizarPlato(plato,id);
    }

    @Override
    public Page<Plato> obtenerPlatos(Pageable pageable, Long idRestaurante, Long idCategoria) {
        Page<Plato> platos = platoPersistencePort.obtenerPlatos(pageable,idRestaurante,idCategoria);
        return platos.map(plato -> new Plato(
                plato.getNombre(),
                plato.getPrecio(),
                plato.getDescripcion(),
                plato.getUrlImagen(),
                plato.getIdCategoria(),
                categoriaPersistencePort.obtenerCategoriaPorId(plato.getIdCategoria()).getNombre(),
                plato.getActivo(),
                plato.getIdRestaurante()
        ));
    }
}
