package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.exception.NoEsPropietarioException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.ISeguridadContextPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlatoUseCaseTest {

    private Plato plato;
    @Mock
    private IPlatoPersistencePort platoPersistencePort;
    @Mock
    IRestaurantePersistencePort restaurantePersistencePort;
    @Mock
    private ISeguridadContextPort seguiridadContextPort;

    @InjectMocks
    private PlatoUseCase platoUseCase;

    @BeforeEach
    void setUp() {
        plato = new Plato("plato",100,
                "plato desc", "http://logo.jpg",
                1L,false,1L);
    }

    @Test
    void guardarPlato_CuenadoRestauranteExiste_GuardarPlatoActivo() {

        when(restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())).
                thenReturn(true);
        when(seguiridadContextPort.esPropietarioDeRestaurante(plato.getIdRestaurante())).thenReturn(true);
        
        platoUseCase.guardarPlato(plato);

        assertTrue(plato.getActivo(), "El plato debe estar activo por defecto");

        verify(restaurantePersistencePort).existeRestaurantePorId(1L);
        verify(platoPersistencePort).guardarPlato(plato);
    }

    @Test
    void guardarPlato_CuandoRestauranteNoExiste_LanzaExcepcion() {

        when(restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())).
                thenReturn(false);

        RestauranteNoEncontradoException exception =
                assertThrows(RestauranteNoEncontradoException.class,() ->{
            platoUseCase.guardarPlato(plato);
        });

        assertEquals("Restaurante no encontrado.",exception.getMessage());

        assertFalse(plato.getActivo(),"El plato no debe estar activo");

        verify(restaurantePersistencePort).existeRestaurantePorId(1L);
        verify(platoPersistencePort,never()).guardarPlato(plato);
    }


    @Test
    void actualizarPlato_correectamente_devuelvePlatoActualizado() {
        Plato platoActualizado = new Plato("plato",100,
                "nueva descripcion","http://otro",
                1L,true,1L);
        when(seguiridadContextPort.esPropietarioDeRestaurante(plato.getIdRestaurante())).thenReturn(true);
        when(platoPersistencePort.actualizarPlato(plato,1L)).thenReturn(platoActualizado);

        Plato resultado = platoUseCase.actualizarPlato(plato,1L);

        assertEquals(platoActualizado,resultado);
        verify(platoPersistencePort).actualizarPlato(plato,1L);
    }

    @Test
    void guardarPlato_NoEsElPropietario_lanzaExcepcion() {
        when(restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())).
                thenReturn(true);
        when(seguiridadContextPort.esPropietarioDeRestaurante(plato.getIdRestaurante())).thenReturn(false);

        NoEsPropietarioException exception = assertThrows(NoEsPropietarioException.class, () -> {
            platoUseCase.guardarPlato(plato);
        });

        assertEquals("No es propietario del restaurante.",exception.getMessage());

        verify(seguiridadContextPort).esPropietarioDeRestaurante(1L);
        verify(platoPersistencePort,never()).guardarPlato(plato);
    }

    @Test
    void actualizarPlato_NoEsElPropietario_lanzaExcepcion() {
        when(seguiridadContextPort.esPropietarioDeRestaurante(plato.getIdRestaurante())).thenReturn(false);

        NoEsPropietarioException exception = assertThrows(NoEsPropietarioException.class, () -> {
            platoUseCase.actualizarPlato(plato,1L);
        });

        assertEquals("No es propietario del restaurante.",exception.getMessage());

        verify(seguiridadContextPort).esPropietarioDeRestaurante(1L);
        verify(platoPersistencePort,never()).actualizarPlato(plato,1L);
    }
}