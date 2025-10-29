package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.exception.NoEsPropietarioException;
import com.plazoleta.plazoleta.domain.exception.NombreRestauranteInvalidoException;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.validations.IRestauranteValidador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteUseCaseTest {

    private Restaurante restaurante;

    @Mock
    private IRestaurantePersistencePort restaurantePersistencePort;

    @Mock
    private IRestauranteValidador restauranteValidador;

    @InjectMocks
    private RestauranteUseCase restauranteUseCase;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("local",
                "124545","cra 32 -58", "3125896547",
                "http://logo.jpg",1L);
    }

    @Test
    void guardarRestaurante_correctamente() {

        restauranteUseCase.guardarRestaurante(restaurante);

        verify(restauranteValidador).validarNombreRestaurante("local");
        verify(restauranteValidador).validarPropietarioRestaurante(1L);
        verify(restaurantePersistencePort).guardarRestaurante(restaurante);
    }

    @Test
    void guardarRestaurante_CuandoTieneSoloNumerosEnNombre() {
        restaurante.setNombre("124545");

        doThrow(new NombreRestauranteInvalidoException("Nombre del restaurante invalido"))
                .when(restauranteValidador).validarNombreRestaurante("124545");

        NombreRestauranteInvalidoException exception = assertThrows(NombreRestauranteInvalidoException.class, () -> {
            restauranteUseCase.guardarRestaurante(restaurante);
        });

        assertEquals("Nombre del restaurante invalido",exception.getMessage());

        verify(restauranteValidador).validarNombreRestaurante("124545");
        verify(restauranteValidador,never()).validarPropietarioRestaurante(anyLong());
        verify(restaurantePersistencePort,never()).guardarRestaurante(restaurante);
    }

    @Test
    void guardarRestaurante_CuandoNoEsUnPropietario(){

        doThrow(new NoEsPropietarioException("Usuario invalido."))
                .when(restauranteValidador).validarPropietarioRestaurante(1L);

        NoEsPropietarioException exception = assertThrows(NoEsPropietarioException.class, () -> {
            restauranteUseCase.guardarRestaurante(restaurante);
        });

        assertEquals("Usuario invalido.",exception.getMessage());

        verify(restauranteValidador).validarNombreRestaurante("local");
        verify(restauranteValidador).validarPropietarioRestaurante(1L);
        verify(restaurantePersistencePort,never()).guardarRestaurante(restaurante);
    }

    @Test
    void obtenerRestaurantes_correctamente() {
        List<Restaurante> restaurantes = List.of(
                new Restaurante("Restaurante 1", "12345", "Dirección 1", "3111111111", "http://logo1.jpg", 1L),
                new Restaurante("Restaurante 2", "12346", "Dirección 2", "3222222222", "http://logo2.jpg", 2L)
        );

        Page<Restaurante> page = new PageImpl<>(restaurantes);

        when(restaurantePersistencePort.obtenerRestaurantes(Pageable.unpaged())).thenReturn(page);

        Page<Restaurante> restaurantesResultado = restauranteUseCase.obtenerRestaurantes(Pageable.unpaged());

        assertEquals(page,restaurantesResultado);

        verify(restaurantePersistencePort).obtenerRestaurantes(Pageable.unpaged());
    }
}