package com.plazoleta.plazoleta.infraestructure.out.jpa.mapper;

import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.PedidoEntity;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.PedidoPlatoEntity;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface PedidoEntityMapper {
    @Mapping(target = "pedidoPlatos", source = "pedidoPlatos")
    PedidoEntity toPedidoEntity(Pedido pedido);

    @AfterMapping
    default void setPedidoBidirectional(@MappingTarget PedidoEntity pedidoEntity) {
        if (pedidoEntity.getPedidoPlatos() != null) {
            for (PedidoPlatoEntity plato : pedidoEntity.getPedidoPlatos()) {
                plato.setPedido(pedidoEntity);
            }
        }
    }

    Pedido toPedido(PedidoEntity pedidoEntity);

    default Page<Pedido> toPagePedido(Page<PedidoEntity> page){
        return page.map(this::toPedido);
    }

    @Mapping(target = "pedido", ignore = true)
    PedidoPlato pedidoPlatoEntityToPedidoPlato(PedidoPlatoEntity pedidoPlatoEntity);

    List<PedidoPlato> pedidoPlatoEntityListToPedidoPlatoList(List<PedidoPlatoEntity> list);
}
