package com.plazoleta.plazoleta.infraestructure.out.jpa.adapter;

import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.spi.IPedidoPersistencePort;
import com.plazoleta.plazoleta.infraestructure.out.jpa.mapper.PedidoEntityMapper;
import com.plazoleta.plazoleta.infraestructure.out.jpa.repository.IPedidoRepository;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;

@RequiredArgsConstructor
public class PedidoJpaAdapter implements IPedidoPersistencePort {

    private final IPedidoRepository pedidoRepository;
    private final PedidoEntityMapper pedidoEntityMapper;

    @Override
    public void guardarPedido(Pedido pedido) {
        pedidoRepository.save(pedidoEntityMapper.toPedidoEntity(pedido));
    }

    @Override
    public boolean existePedidoActivoPorCliente(Long idCliente, EnumSet<Estado> estadosBloqueantes) {
        return pedidoRepository.existsPedidoActivoPorCliente(idCliente, estadosBloqueantes);
    }
}
