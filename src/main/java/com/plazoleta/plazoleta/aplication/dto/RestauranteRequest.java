package com.plazoleta.plazoleta.aplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteRequest {
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private Long idUsuario;
}
