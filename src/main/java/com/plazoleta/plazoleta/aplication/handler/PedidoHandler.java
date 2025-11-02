package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.PedidoRequest;
import com.plazoleta.plazoleta.aplication.dto.PedidosResponse;
import com.plazoleta.plazoleta.aplication.mapper.PedidoRequestMapper;
import com.plazoleta.plazoleta.aplication.mapper.PedidoResponseMapper;
import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoHandler implements IPedidoHandler{

    private final IPedidoServicePort pedidoServicePort;
    private final PedidoRequestMapper pedidoMapper;
    private final PedidoResponseMapper pedidoResponseMapper;

    @Override
    public void guardarPedido(PedidoRequest pedidoRequest) {
        Pedido pedido = pedidoMapper.toPedido(pedidoRequest);
        List<PedidoPlato> pedidoPlatoList = pedidoMapper.toPedidoPlatoList(pedidoRequest.getPlatos());
        pedido.setPedidoPlatos(pedidoPlatoList);
        pedidoServicePort.guardarPedido(pedido);
    }

    @Override
    public Page<PedidosResponse> obtenerPedidos(Long idEmpleado, Estado estado, Pageable pageable) {
        return pedidoResponseMapper.pedidosToPedidosResponse(pedidoServicePort.obtenerPedidos(idEmpleado,estado,pageable));
    }

    @Override
    public PedidosResponse asignarPedido(Long idPedido) {
        return pedidoResponseMapper.pedidoToPedidosResponse(pedidoServicePort.asignarPedido(idPedido));
    }
}
