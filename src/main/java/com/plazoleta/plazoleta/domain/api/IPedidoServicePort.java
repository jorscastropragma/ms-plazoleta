package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.Pedido;

public interface IPedidoServicePort {
    void guardarPedido(Pedido pedido);
}
