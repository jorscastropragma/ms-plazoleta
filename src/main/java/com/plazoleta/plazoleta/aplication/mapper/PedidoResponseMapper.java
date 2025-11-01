package com.plazoleta.plazoleta.aplication.mapper;

import com.plazoleta.plazoleta.aplication.dto.PedidoPlatosResponse;
import com.plazoleta.plazoleta.aplication.dto.PedidosResponse;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PedidoResponseMapper {
    @Mapping(target = "platos", source = "pedidoPlatos")
    PedidosResponse pedidoToPedidosResponse(Pedido pedido);
    PedidoPlatosResponse pedidoPlatoToPedidoPlatosResponse(PedidoPlato pedidoPlato);

    default Page<PedidosResponse> pedidosToPedidosResponse(Page<Pedido> pedidos){
        return pedidos.map(this::pedidoToPedidosResponse);
    }
}
