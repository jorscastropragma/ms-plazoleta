package com.plazoleta.plazoleta.infraestructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos_platos")
@RequiredArgsConstructor
@Getter
@Setter
public class PedidoPlatoEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;
    private Long idPlato;
    private Integer cantidad;
}
