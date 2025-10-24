package com.plazoleta.plazoleta.aplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRequest {
    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "El nit es obligatorio.")
    @Pattern(regexp = "^[0-9]+$", message = "El campo nit debe contener solo números")
    private String nit;

    @NotBlank(message = "La direccion es obligatoria.")
    private String direccion;

    @NotBlank(message = "El telefono es obligatorio.")
    @Pattern(regexp = "^\\+?\\d{10,13}$", message = "El campo Telefono debe contener un máximo de 13 caracteres y puede contener el símbolo +")
    private String telefono;

    @NotBlank(message = "La url del logo es obligatoria.")
    private String urlLogo;

    @NotNull(message = "El id del usuario es obligatorio.")
    private Long idUsuario;
}
