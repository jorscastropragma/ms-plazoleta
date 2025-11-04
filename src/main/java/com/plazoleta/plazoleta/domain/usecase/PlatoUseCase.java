package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.exception.MensajeDomainException;
import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
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
    public void guardarPlato(Plato plato, String emailUsuario) {
        if (!restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())){
            throw new ReglaDeNegocioInvalidaException(MensajeDomainException.RESTAURANTE_NO_ENCONTRADO.getMensaje());
        }
        if (!seguiridadContextPort.esPropietarioDeRestaurante(plato.getIdRestaurante(), emailUsuario)){
            throw new ReglaDeNegocioInvalidaException(MensajeDomainException.NO_ES_EL_PROPIETARIO_DEL_RESTAURANTE.getMensaje());
        }
        plato.setActivo(true);
        platoPersistencePort.guardarPlato(plato);
    }

    @Override
    public Plato actualizarPlato(Plato plato, Long idPlato, String emailUsuario) {
        if (!seguiridadContextPort.esPropietarioDePlato(idPlato,emailUsuario)){
            throw new ReglaDeNegocioInvalidaException(MensajeDomainException.NO_ES_EL_PROPIETARIO_DEL_PLATO.getMensaje());
        }
        return platoPersistencePort.actualizarPlato(plato,idPlato);
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
