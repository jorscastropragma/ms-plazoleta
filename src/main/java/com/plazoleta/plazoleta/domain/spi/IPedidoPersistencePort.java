package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.EnumSet;

public interface IPedidoPersistencePort {
    void guardarPedido(Pedido pedido);
    boolean existePedidoActivoPorCliente(Long idCliente, EnumSet<Estado> estadosBloqueantes);
    Page<Pedido> obtenerPedidos(Long idRestaurante, Estado estado, Pageable pageable);
    Pedido asignarPedido(Pedido pedido);
    Pedido obtenerPedido(Long idPedido);
}
