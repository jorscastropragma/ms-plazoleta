package com.plazoleta.plazoleta.infraestructure.out.jpa.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "restaurantes_empleados")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RestauranteEmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idRestaurante;
    private Long idEmpleado;
}
