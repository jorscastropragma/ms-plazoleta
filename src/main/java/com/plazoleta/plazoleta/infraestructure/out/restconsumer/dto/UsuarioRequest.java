package com.plazoleta.plazoleta.infraestructure.out.restconsumer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UsuarioRequest {
    private String nombre;
    private String apellido;
    private Long documentoIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String rol;
}
