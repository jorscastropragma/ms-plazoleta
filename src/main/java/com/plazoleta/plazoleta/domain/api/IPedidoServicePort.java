package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;

import java.util.List;

public interface IPedidoServicePort {
    void guardarPedido(Pedido pedido);
}
