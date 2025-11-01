package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.spi.IPedidoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.validations.PedidoValidador;



public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IPlatoPersistencePort platoPersistencePort;
    private final PedidoValidador pedidoValidador;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                         IRestaurantePersistencePort restaurantePersistencePort,
                         IPlatoPersistencePort platoPersistencePort,
                         PedidoValidador pedidoValidador) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.platoPersistencePort = platoPersistencePort;
        this.pedidoValidador = pedidoValidador;
    }

    @Override
    public void guardarPedido(Pedido pedido) {
        pedidoValidador.validarEstadoPedidoCLiente(pedidoPersistencePort,pedido.getIdCliente());
        pedidoValidador.validarRestaunteExista(restaurantePersistencePort,pedido.getIdRestaurante());
        pedidoValidador.validarPlatoExistaYMismoRestaurante(platoPersistencePort,pedido);

        pedido.setEstado(Estado.PENDIENTE);
        pedidoPersistencePort.guardarPedido(pedido);
    }
}
