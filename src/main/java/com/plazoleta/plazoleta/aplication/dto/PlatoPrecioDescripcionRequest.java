package com.plazoleta.plazoleta.aplication.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlatoPrecioDescripcionRequest {
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precio;

    private String descripcion;
}
