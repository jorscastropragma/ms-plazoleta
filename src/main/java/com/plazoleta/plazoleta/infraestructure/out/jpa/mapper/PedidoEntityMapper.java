package com.plazoleta.plazoleta.infraestructure.out.jpa.mapper;

import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.PedidoEntity;
import com.plazoleta.plazoleta.infraestructure.out.jpa.entity.PedidoPlatoEntity;
import org.mapstruct.*;

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

}
