package com.plazoleta.plazoleta.aplication.dto;

import com.plazoleta.plazoleta.domain.model.Estado;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidosResponse {
    Long id;
    Long idCliente;
    LocalDateTime fecha;
    Estado estado;
    Long idRestaurante;
    Long idEmpleadoAsignado;
    List<PedidoPlatosResponse> platos;
}
