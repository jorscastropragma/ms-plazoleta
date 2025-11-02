package com.plazoleta.plazoleta.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private Long id;
    private Long idCliente;
    LocalDateTime fecha;
    Estado estado;
    Long idRestaurante;
    Long idEmpleadoAsignado;
    List<PedidoPlato> pedidoPlatos;

    public Pedido(Long id, Long idCliente, LocalDateTime fecha, Estado estado, Long idRestaurante,Long idEmpleadoAsignado, List<PedidoPlato> pedidoPlatos) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.estado = estado;
        this.idRestaurante = idRestaurante;
        this.idEmpleadoAsignado = idEmpleadoAsignado;
        this.pedidoPlatos = pedidoPlatos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(Long idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public Long getIdEmpleadoAsignado() {
        return idEmpleadoAsignado;
    }

    public void setIdEmpleadoAsignado(Long idEmpleadoAsignado) {
        this.idEmpleadoAsignado = idEmpleadoAsignado;
    }

    public List<PedidoPlato> getPedidoPlatos() {
        return pedidoPlatos;
    }
    public void setPedidoPlatos(List<PedidoPlato> pedidoPlatos) {
        this.pedidoPlatos = pedidoPlatos;
    }
}
