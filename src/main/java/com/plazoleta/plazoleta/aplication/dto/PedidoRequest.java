package com.plazoleta.plazoleta.aplication.dto;

import com.plazoleta.plazoleta.domain.model.Estado;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoRequest {
    private Long idCliente;
    LocalDateTime fecha;
    Long idRestaurante;
    List<PedidoPlatosRequest> platos;
}
