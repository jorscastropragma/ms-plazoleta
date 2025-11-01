package com.plazoleta.plazoleta.domain.validations;

import com.plazoleta.plazoleta.domain.exception.MensajeDomainException;
import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.IPedidoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;

import java.util.EnumSet;

public class PedidoValidador {

    public void validarEstadoPedidoCLiente(IPedidoPersistencePort pedidoPersistencePort, Long idCliente){
        var estadosBloqueo = EnumSet.of(Estado.EN_PREPARACION, Estado.PENDIENTE, Estado.LISTO);
        if (pedidoPersistencePort.existePedidoActivoPorCliente(idCliente, estadosBloqueo)){
            throw new ReglaDeNegocioInvalidaException(MensajeDomainException.PEDIDO_EXISTE.getMensaje());
        }
    }

    public void validarRestaunteExista(IRestaurantePersistencePort restaurantePersistencePort, Long idRestaurante){
        if (!restaurantePersistencePort.existeRestaurantePorId(idRestaurante)){
            throw new ReglaDeNegocioInvalidaException(MensajeDomainException.RESTAURANTE_NO_ENCONTRADO.getMensaje());
        }
    }

    public void validarPlatoExistaYMismoRestaurante(IPlatoPersistencePort platoPersistencePort, Pedido pedido){

        pedido.getPedidoPlatos().parallelStream().forEach(pedidoPlato -> {
            Plato plato = platoPersistencePort.obtenerPlatoPorId(pedidoPlato.getIdPlato());
            if (plato == null){
                throw new ReglaDeNegocioInvalidaException(MensajeDomainException.PLATO_NO_ENCONTRADO.getMensaje());
            }
            if (!plato.getIdRestaurante().equals(pedido.getIdRestaurante())){
                throw new ReglaDeNegocioInvalidaException(MensajeDomainException.PLATO_DIFERENTE_RESTAURANTE.getMensaje());
            }
        });
    }
}
