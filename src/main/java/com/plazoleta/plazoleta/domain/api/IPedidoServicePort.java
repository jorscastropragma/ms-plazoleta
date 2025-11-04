package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPedidoServicePort {
    void guardarPedido(Pedido pedido);
    Page<Pedido> obtenerPedidos(Long idEmpleado, Estado estado, Pageable pageable);
    Pedido asignarPedido(Long idPedido, Long idEmpleado);
}
