package com.plazoleta.plazoleta.aplication.mapper;


import com.plazoleta.plazoleta.aplication.dto.PedidoPlatosRequest;
import com.plazoleta.plazoleta.aplication.dto.PedidoRequest;
import com.plazoleta.plazoleta.aplication.dto.PedidosResponse;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PedidoRequestMapper {
    Pedido toPedido(PedidoRequest pedidoRequest);
    PedidoPlato toPedidoPlato(PedidoPlatosRequest pedidoPlatosRequest);
    List<PedidoPlato> toPedidoPlatoList(List<PedidoPlatosRequest> pedidoPlatosRequest);

}
