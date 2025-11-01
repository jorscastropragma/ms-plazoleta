package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;

import java.util.EnumSet;

public interface IPedidoPersistencePort {
    void guardarPedido(Pedido pedido);
    boolean existePedidoActivoPorCliente(Long idCliente, EnumSet<Estado> estadosBloqueantes);
}
