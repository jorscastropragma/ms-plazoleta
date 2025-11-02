package com.plazoleta.plazoleta.domain.validations;

import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.IPedidoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoValidadorTest {

    @Mock
    IPedidoPersistencePort pedidoPersistencePort;
    @Mock
    IRestaurantePersistencePort restaurantePersistencePort;
    @Mock
    IPlatoPersistencePort platoPersistencePort;

    PedidoValidador validador;

    @BeforeEach
    void setUp() {
        validador = new PedidoValidador();
    }

    private Pedido buildPedido(Long idCliente, Long idRestaurante, Long... idPlatos) {
        var pedido = new Pedido(
                1L,
                idCliente,
                LocalDateTime.now(),
                Estado.PENDIENTE,
                idRestaurante,
                null,
                List.of()
        );
        var detalles = java.util.Arrays.stream(idPlatos)
                .map(idPlato -> new PedidoPlato(pedido, idPlato, 1))
                .toList();
        pedido.setPedidoPlatos(detalles);
        return pedido;
    }

    @Test
    void validarEstadoPedidoCLiente_cuandoExistePedidoActivo_lanzaExcepcion() {
        when(pedidoPersistencePort.existePedidoActivoPorCliente(eq(99L), any(EnumSet.class)))
                .thenReturn(true);

        assertThrows(ReglaDeNegocioInvalidaException.class,
                () -> validador.validarEstadoPedidoCLiente(pedidoPersistencePort, 99L));
    }

    @Test
    void validarEstadoPedidoCLiente_cuandoNoExistePedidoActivo_noLanza() {
        when(pedidoPersistencePort.existePedidoActivoPorCliente(eq(50L), any(EnumSet.class)))
                .thenReturn(false);

        assertDoesNotThrow(() -> validador.validarEstadoPedidoCLiente(pedidoPersistencePort, 50L));
    }


    @Test
    void validarRestaunteExista_siNoExiste_lanzaExcepcion() {
        when(restaurantePersistencePort.existeRestaurantePorId(7L)).thenReturn(false);

        assertThrows(ReglaDeNegocioInvalidaException.class,
                () -> validador.validarRestaunteExista(restaurantePersistencePort, 7L));
    }

    @Test
    void validarRestaunteExista_siExiste_noLanza() {
        when(restaurantePersistencePort.existeRestaurantePorId(7L)).thenReturn(true);

        assertDoesNotThrow(() -> validador.validarRestaunteExista(restaurantePersistencePort, 7L));
    }

    @Test
    void validarPlatoExistaYMismoRestaurante_siUnPlatoEsNull_lanzaExcepcion() {
        var pedido = buildPedido(10L, 3L, 100L, 101L);

        when(platoPersistencePort.obtenerPlatoPorId(101L)).thenReturn(null);

        assertThrows(ReglaDeNegocioInvalidaException.class,
                () -> validador.validarPlatoExistaYMismoRestaurante(platoPersistencePort, pedido));
    }

    @Test
    void validarPlatoExistaYMismoRestaurante_siPlatoDeOtroRestaurante_lanzaExcepcion() {
        var pedido = buildPedido(10L, 3L, 200L);

        when(platoPersistencePort.obtenerPlatoPorId(200L)).thenReturn(plato( 4L));

        assertThrows(ReglaDeNegocioInvalidaException.class,
                () -> validador.validarPlatoExistaYMismoRestaurante(platoPersistencePort, pedido));
    }

    @Test
    void validarPlatoExistaYMismoRestaurante_todosExistenYDelMismoRestaurante_noLanza() {
        var pedido = buildPedido(10L, 5L, 300L, 301L, 302L);

        when(platoPersistencePort.obtenerPlatoPorId(300L)).thenReturn(plato(5l));
        when(platoPersistencePort.obtenerPlatoPorId(301L)).thenReturn(plato( 5L));
        when(platoPersistencePort.obtenerPlatoPorId(302L)).thenReturn(plato(5L));

        assertDoesNotThrow(() -> validador.validarPlatoExistaYMismoRestaurante(platoPersistencePort, pedido));

        verify(platoPersistencePort, times(1)).obtenerPlatoPorId(300L);
        verify(platoPersistencePort, times(1)).obtenerPlatoPorId(301L);
        verify(platoPersistencePort, times(1)).obtenerPlatoPorId(302L);
    }

    private Plato plato(Long idRestaurante) {
        return new Plato("arroz",
                12000,
                "con pollo",
                "http://logo.jpg",
                1L,
                "POSTRE",
                false,
                idRestaurante);
    }
}