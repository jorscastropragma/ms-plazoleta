package com.plazoleta.plazoleta.aplication.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoRequest {
    @NotNull(message = "El campo idCliente es obligatorio")
    Long idCliente;
    @NotNull(message = "El campo fecha es obligatorio")
    LocalDateTime fecha;
    @NotNull(message = "El campo idRestaurante es obligatorio")
    Long idRestaurante;
    @NotNull(message = "El campo platos es obligatorio")
    List<PedidoPlatosRequest> platos;
}
