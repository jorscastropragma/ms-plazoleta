package com.plazoleta.plazoleta.aplication.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteEmpleadoRequest {
    @NotNull(message = "El campo 'idRestaurante' es obligatorio")
    private Long idRestaurante;
    @NotNull(message = "El campo 'idEmpleado' es obligatorio")
    private Long idEmpleado;
}
