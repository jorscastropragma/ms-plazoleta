package com.plazoleta.plazoleta.aplication.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoCategoriaResponse {
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String categoria;
}
