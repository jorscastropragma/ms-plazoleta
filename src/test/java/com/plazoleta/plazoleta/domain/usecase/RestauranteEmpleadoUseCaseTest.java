package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.spi.IRestauranteEmpleadoPersistencePort;
import com.plazoleta.plazoleta.infraestructure.exception.RecursoNoEncontradoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteEmpleadoUseCaseTest {

    @Mock
    private IRestauranteEmpleadoPersistencePort restauranteEmpleadoPersistencePort;

    @InjectMocks
    private RestauranteEmpleadoUseCase restauranteEmpleadoUseCase;

    @Test
    void guardarRestauranteEmpleado_CuandoDatosSonValidos_DeberiaLlamarAlPort() {
        Long idRestaurante = 1L;
        Long idEmpleado = 2L;


        restauranteEmpleadoUseCase.guardarRestauranteEmpleado(idRestaurante, idEmpleado);


        verify(restauranteEmpleadoPersistencePort, times(1))
                .guardarRestauranteEmpleado(idRestaurante, idEmpleado);
    }

    @Test
    void guardarRestauranteEmpleado_CuandoIdRestauranteEsNull_DeberiaPropagarExcepcion() {
        Long idRestaurante = null;
        Long idEmpleado = 2L;

        doThrow(new RecursoNoEncontradoException("No hay restaurantes registrados."))
                .when(restauranteEmpleadoPersistencePort)
                .guardarRestauranteEmpleado(null, idEmpleado);

        RecursoNoEncontradoException exception = assertThrows(
                RecursoNoEncontradoException.class,
                () -> restauranteEmpleadoUseCase.guardarRestauranteEmpleado(idRestaurante, idEmpleado)
        );

        assertEquals("No hay restaurantes registrados.", exception.getMessage());
        verify(restauranteEmpleadoPersistencePort, times(1))
                .guardarRestauranteEmpleado(null, idEmpleado);
    }
}