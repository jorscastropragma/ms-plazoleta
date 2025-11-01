package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.PedidoRequest;
import com.plazoleta.plazoleta.aplication.dto.PedidosResponse;
import com.plazoleta.plazoleta.domain.model.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPedidoHandler {
    void guardarPedido(PedidoRequest pedidoRequest);
    Page<PedidosResponse> obtenerPedidos(Long idEmpleado, Estado estado, Pageable pageable);
}
