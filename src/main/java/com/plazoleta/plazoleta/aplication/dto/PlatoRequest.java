package com.plazoleta.plazoleta.aplication.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlatoRequest {
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotNull(message = "El precio es obligatorio.")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precio;

    @NotBlank(message = "La descripcion es obligatoria.")
    private String descripcion;

    @NotBlank(message = "La url de la imagen es obligatoria.")
    private String urlImagen;

    @NotBlank(message = "La categoria es obligatoria.")
    private String categoria;

    @NotNull(message = "El id del restaurante es obligatorio.")
    private Long idRestaurante;
}
