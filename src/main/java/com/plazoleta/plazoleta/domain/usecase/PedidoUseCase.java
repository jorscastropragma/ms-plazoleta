package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.exception.MensajeDomainException;
import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.spi.*;
import com.plazoleta.plazoleta.domain.validations.PedidoValidador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class PedidoUseCase implements IPedidoServicePort {

    private final IPedidoPersistencePort pedidoPersistencePort;
    private final IRestaurantePersistencePort restaurantePersistencePort;
    private final IPlatoPersistencePort platoPersistencePort;
    private final PedidoValidador pedidoValidador;
    private final IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort;
    private final ISeguridadContextPort seguridadContextPort;

    public PedidoUseCase(IPedidoPersistencePort pedidoPersistencePort,
                         IRestaurantePersistencePort restaurantePersistencePort,
                         IPlatoPersistencePort platoPersistencePort,
                         PedidoValidador pedidoValidador,
                         IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort,
                         ISeguridadContextPort seguridadContextPort) {
        this.pedidoPersistencePort = pedidoPersistencePort;
        this.restaurantePersistencePort = restaurantePersistencePort;
        this.platoPersistencePort = platoPersistencePort;
        this.pedidoValidador = pedidoValidador;
        this.restauranteEmpleadoPersistencePort = restauranteEmpleadoPersistencePort;
        this.seguridadContextPort = seguridadContextPort;
    }

    @Override
    public void guardarPedido(Pedido pedido) {
        pedidoValidador.validarEstadoPedidoCLiente(pedidoPersistencePort,pedido.getIdCliente());
        pedidoValidador.validarRestaunteExista(restaurantePersistencePort,pedido.getIdRestaurante());
        pedidoValidador.validarPlatoExistaYMismoRestaurante(platoPersistencePort,pedido);

        pedido.setEstado(Estado.PENDIENTE);
        pedidoPersistencePort.guardarPedido(pedido);
    }

    @Override
    public Page<Pedido> obtenerPedidos(Long idEmpleado, Estado estado, Pageable pageable) {
        Long idRestaurante = restauranteEmpleadoPersistencePort.obtenerIdRestaurantePorEmpleado(idEmpleado);
        Page<Pedido> pedidos = pedidoPersistencePort.obtenerPedidos(idRestaurante,estado,pageable);
        if (pedidos.isEmpty() && pageable.getPageSize() == 1){
            throw new ReglaDeNegocioInvalidaException(MensajeDomainException.PEDIDO_EXISTE.getMensaje());
        }
        return pedidos;
    }

    @Override
    public Pedido asignarPedido(Long idPedido, Long idEmpleado) {
        Pedido pedido = pedidoPersistencePort.obtenerPedido(idPedido);
        if (pedido.getEstado() != Estado.PENDIENTE){
            throw new ReglaDeNegocioInvalidaException(MensajeDomainException.PEDIDO_DIFERENTE_PENDIENTE.getMensaje());
        }
        pedido.setEstado(Estado.EN_PREPARACION);
        System.out.println("idEmpleado usecase: " + idEmpleado);
        pedido.setIdEmpleadoAsignado(idEmpleado);
        return pedidoPersistencePort.asignarPedido(pedido);
    }


}
