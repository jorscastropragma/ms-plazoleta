package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.api.IPedidoServicePort;
import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
import com.plazoleta.plazoleta.domain.model.Estado;
import com.plazoleta.plazoleta.domain.model.Pedido;
import com.plazoleta.plazoleta.domain.model.PedidoPlato;
import com.plazoleta.plazoleta.domain.spi.*;
import com.plazoleta.plazoleta.domain.validations.PedidoValidador;
import com.plazoleta.plazoleta.infraestructure.exception.MensajeInfraestructuraException;
import com.plazoleta.plazoleta.infraestructure.exception.RecursoNoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
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
    @Mock
    ISeguridadContextPort seguridadContextPort;

    private IPedidoServicePort useCase;
    private Long idEmpleado = 1L;


    @BeforeEach
    void setUp() {
        useCase = new PedidoUseCase(
                pedidoPersistencePort,
                restaurantePersistencePort,
                platoPersistencePort,
                pedidoValidador,
                restauranteEmpleadoPersistencePort,
                seguridadContextPort
        );
    }

    private Pedido buildPedido(Long idCliente, Long idRestaurante, Estado estadoInicial) {
        var pedido = new Pedido(
                1L,
                idCliente,
                LocalDateTime.now(),
                estadoInicial,
                idRestaurante,
                null,
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
    void obtenerPedidos_CuandoExistenPedidos_RetornaPageCorrectamente() {
        Long idEmpleado = 1L;
        Long idRestaurante = 100L;
        Estado estado = Estado.PENDIENTE;
        Pageable pageable = PageRequest.of(0, 10);
        Pedido pedido = new Pedido(1l, 1l, LocalDateTime.now(), estado, idRestaurante,null, List.of());

        List<Pedido> pedidosList = List.of(pedido, new Pedido(1l, 1l, LocalDateTime.now(), estado, idRestaurante,null, List.of()));
        Page<Pedido> expectedPage = new PageImpl<>(pedidosList, pageable, pedidosList.size());

        when(restauranteEmpleadoPersistencePort.obtenerIdRestaurantePorEmpleado(idEmpleado))
                .thenReturn(idRestaurante);
        when(pedidoPersistencePort.obtenerPedidos(idRestaurante, estado, pageable))
                .thenReturn(expectedPage);

        Page<Pedido> result = useCase.obtenerPedidos(idEmpleado, estado, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(expectedPage, result);

        verify(restauranteEmpleadoPersistencePort).obtenerIdRestaurantePorEmpleado(idEmpleado);
        verify(pedidoPersistencePort).obtenerPedidos(idRestaurante, estado, pageable);
    }

    @Test
    void obtenerPedidos_CuandoEmpleadoNoTieneRestaurante_LanzaExcepcion() {
        Long idEmpleado = 1L;
        Estado estado = Estado.PENDIENTE;
        Pageable pageable = PageRequest.of(0, 10);
        when(restauranteEmpleadoPersistencePort.obtenerIdRestaurantePorEmpleado(idEmpleado))
                .thenThrow(new RecursoNoEncontradoException("No hay restaurantes registrados."));


        RecursoNoEncontradoException exception = assertThrows(
                RecursoNoEncontradoException.class,
                () -> useCase.obtenerPedidos(idEmpleado, estado, pageable)
        );

        assertEquals("No hay restaurantes registrados.", exception.getMessage());

        verify(restauranteEmpleadoPersistencePort).obtenerIdRestaurantePorEmpleado(idEmpleado);
        verify(pedidoPersistencePort, never()).obtenerPedidos(any(), any(), any());
    }

    @Test
    void obtenerPedidos_CuandoNoExistenPedidosYPageSizeEsUno_LanzaExcepcion() {
        Long idEmpleado = 1L;
        Estado estado = Estado.PENDIENTE;
        Long idRestaurante = 100L;

        Pageable pageSizeUno = PageRequest.of(0, 1);
        Page<Pedido> emptyPage = new PageImpl<>(Collections.emptyList(), pageSizeUno, 0);

        when(restauranteEmpleadoPersistencePort.obtenerIdRestaurantePorEmpleado(idEmpleado))
                .thenReturn(idRestaurante);
        when(pedidoPersistencePort.obtenerPedidos(idRestaurante, estado, pageSizeUno))
                .thenReturn(emptyPage);
        
        ReglaDeNegocioInvalidaException exception = assertThrows(
                ReglaDeNegocioInvalidaException.class,
                () -> useCase.obtenerPedidos(idEmpleado, estado, pageSizeUno)
        );

        assertEquals("Ya existe un pedido activo para este cliente", exception.getMessage()); // Ajusta según tu MensajeDomainException

        verify(restauranteEmpleadoPersistencePort).obtenerIdRestaurantePorEmpleado(idEmpleado);
        verify(pedidoPersistencePort).obtenerPedidos(idRestaurante, estado, pageSizeUno);
    }

    @Test
    void asignarPedido_CuandoPedidoExiste_DeberiaAsignarCorrectamente() {
        Long idPedido = 1L;
        Long idEmpleadoAutenticado = 10L;

        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(idPedido);
        pedidoExistente.setEstado(Estado.PENDIENTE);
        pedidoExistente.setIdEmpleadoAsignado(null);

        Pedido pedidoModificado = new Pedido();
        pedidoModificado.setId(idPedido);
        pedidoModificado.setEstado(Estado.EN_PREPARACION);
        pedidoModificado.setIdEmpleadoAsignado(idEmpleadoAutenticado);

        when(pedidoPersistencePort.obtenerPedido(idPedido)).thenReturn(pedidoExistente);
        when(seguridadContextPort.obtenerIdUsuarioAutenticado()).thenReturn(idEmpleadoAutenticado);
        when(pedidoPersistencePort.asignarPedido(any(Pedido.class))).thenReturn(pedidoModificado);


        Pedido resultado = useCase.asignarPedido(idPedido,idEmpleado);


        assertNotNull(resultado);
        assertEquals(Estado.EN_PREPARACION, resultado.getEstado());
        assertEquals(idEmpleadoAutenticado, resultado.getIdEmpleadoAsignado());

        verify(pedidoPersistencePort).obtenerPedido(idPedido);
        verify(seguridadContextPort).obtenerIdUsuarioAutenticado();
        verify(pedidoPersistencePort).asignarPedido(argThat(pedido ->
                pedido.getEstado() == Estado.EN_PREPARACION &&
                        pedido.getIdEmpleadoAsignado().equals(idEmpleadoAutenticado)
        ));
    }

    @Test
    void asignarPedido_CuandoPedidoNoExiste_LanzaExcepcion() {

        Long idPedido = 999L;

        when(pedidoPersistencePort.obtenerPedido(idPedido))
                .thenThrow(new RecursoNoEncontradoException(MensajeInfraestructuraException.PEDIDO_NO_ENCONTRADO.getMensaje()));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> useCase.asignarPedido(idPedido,idEmpleado)
        );

        assertEquals("El pedido no existe.", exception.getMessage());
        verify(pedidoPersistencePort).obtenerPedido(idPedido);
        verify(seguridadContextPort, never()).obtenerIdUsuarioAutenticado();
        verify(pedidoPersistencePort, never()).asignarPedido(any());
    }

    @Test
    void asignarPedido_CuandoPedidoEstaEnEstadoListo_LanzaExcepcion() {
        // Arrange
        Long idPedido = 32l;

        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(idPedido);
        pedidoExistente.setEstado(Estado.LISTO);
        pedidoExistente.setIdEmpleadoAsignado(null);

        when(pedidoPersistencePort.obtenerPedido(idPedido))
                .thenReturn(pedidoExistente);
        // Act & Assert
        ReglaDeNegocioInvalidaException exception = assertThrows(
                ReglaDeNegocioInvalidaException.class,
                () -> useCase.asignarPedido(idPedido,idEmpleado)
        );

        assertEquals("El pedido debe estar en estado PENDIENTE", exception.getMessage());
        verify(pedidoPersistencePort).obtenerPedido(32l);
        verify(seguridadContextPort, never()).obtenerIdUsuarioAutenticado();
        verify(pedidoPersistencePort, never()).asignarPedido(any());
    }
}