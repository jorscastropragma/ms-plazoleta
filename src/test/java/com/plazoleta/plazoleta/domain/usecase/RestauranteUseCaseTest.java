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
                .when(restauranteValidador).validarNombreRestaurante("12345");

        NombreRestauranteInvalidoException exception = assertThrows(NombreRestauranteInvalidoException.class, () -> {
            restauranteUseCase.guardarRestaurante(restaurante);
        });

        assertEquals("Nombre del restaurante invalido",exception.getMessage());

        verify(restauranteValidador).validarNombreRestaurante("12345");
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
}