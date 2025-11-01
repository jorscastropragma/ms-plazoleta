package com.plazoleta.plazoleta.domain.model;

public class PedidoPlato {
    private Pedido pedido;
    private Long idPlato;
    private Integer cantidad;

    public PedidoPlato(Pedido pedido, Long idPlato, Integer cantidad) {
        this.pedido = pedido;
        this.idPlato = idPlato;
        this.cantidad = cantidad;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Long getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(Long idPlato) {
        this.idPlato = idPlato;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
