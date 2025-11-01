package com.plazoleta.plazoleta.infraestructure.out.jpa.entity;


import com.plazoleta.plazoleta.domain.model.Estado;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@RequiredArgsConstructor
@Getter
@Setter
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private Long idCliente;
    LocalDateTime fecha;
    @Enumerated(EnumType.STRING)
    Estado estado;
    Long idRestaurante;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoPlatoEntity> pedidoPlatos;
}
