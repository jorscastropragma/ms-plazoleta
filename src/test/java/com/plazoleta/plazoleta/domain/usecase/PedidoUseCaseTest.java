package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import com.plazoleta.plazoleta.domain.spi.IPedidoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestauranteEmpleadoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.validations.PedidoValidador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoUseCaseTest {

    @Mock
    IPedidoPersistencePort pedidoPersistencePort;
    @Mock
    IRestaurantePersistencePort restaurantePersistencePort;
    @Mock
    IPlatoPersistencePort platoPersistencePort;
    @Mock
    PedidoValidador pedidoValidador;
    @Mock
    IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort;

    private IPedidoServicePort useCase;

    @BeforeEach
    void setUp() {
        useCase = new PedidoUseCase(
                pedidoPersistencePort,
                restaurantePersistencePort,
                platoPersistencePort,
                pedidoValidador,
                restauranteEmpleadoPersistencePort
        );
    }

    private Pedido buildPedido(Long idCliente, Long idRestaurante, Estado estadoInicial) {
        var pedido = new Pedido(
                1L,
                idCliente,
                LocalDateTime.now(),
                estadoInicial,
                idRestaurante,
                List.of(new PedidoPlato(null, 10L, 2), new PedidoPlato(null, 11L, 1))
        );
        pedido.getPedidoPlatos().forEach(pp -> pp.setPedido(pedido));
        return pedido;
    }

    @Test
    void guardarPedido_debeValidarEnOrden_ponerEstadoPendiente_yPersistir() {

        var pedido = buildPedido(25L, 7L, Estado.CANCELADO); // estado inicial cualquiera

        useCase.guardarPedido(pedido);

        assertEquals(Estado.PENDIENTE, pedido.getEstado());

        InOrder inOrder = inOrder(pedidoValidador, pedidoPersistencePort);
        inOrder.verify(pedidoValidador).validarEstadoPedidoCLiente(pedidoPersistencePort, 25L);
        inOrder.verify(pedidoValidador).validarRestaunteExista(restaurantePersistencePort, 7L);
        inOrder.verify(pedidoValidador).validarPlatoExistaYMismoRestaurante(platoPersistencePort, pedido);
        inOrder.verify(pedidoPersistencePort).guardarPedido(pedido);

        verifyNoMoreInteractions(pedidoValidador, pedidoPersistencePort, restaurantePersistencePort, platoPersistencePort);
    }

    @Test
    void guardarPedido_siValidadorLanzaExcepcion_noDebePersistir() {
        var pedido = buildPedido(30L, 5L, null);
        doThrow(new RuntimeException("bloqueado")).when(pedidoValidador)
                .validarEstadoPedidoCLiente(pedidoPersistencePort, 30L);

        assertThrows(RuntimeException.class, () -> useCase.guardarPedido(pedido));

        verify(pedidoPersistencePort, never()).guardarPedido(any());
    }
}