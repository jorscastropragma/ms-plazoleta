package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.PedidoPlatosRequest;
import com.plazoleta.plazoleta.aplication.dto.PedidoRequest;
import com.plazoleta.plazoleta.aplication.mapper.PedidoRequestMapper;
import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoHandler implements IPedidoHandler{

    private final IPedidoServicePort pedidoServicePort;
    private final PedidoRequestMapper pedidoMapper;

    @Override
    public void guardarPedido(PedidoRequest pedidoRequest) {
        Pedido pedido = pedidoMapper.toPedido(pedidoRequest);
        List<PedidoPlato> pedidoPlatoList = pedidoMapper.toPedidoPlatoList(pedidoRequest.getPlatos());
        pedido.setPedidoPlatos(pedidoPlatoList);
        pedidoServicePort.guardarPedido(pedido);
    }
}
