package com.plazoleta.plazoleta.aplication.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoPlatosRequest {
    @NotNull(message = "El campo idPlato es obligatorio")
    Long idPlato;
    @Min(value = 1, message = "El campo cantidad debe ser mayor a 0")
    @NotNull(message = "El campo cantidad es obligatorio")
    Integer cantidad;
}
