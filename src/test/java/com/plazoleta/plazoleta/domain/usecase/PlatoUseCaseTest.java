package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
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
    @InjectMocks
    private PlatoUseCase platoUseCase;

    @BeforeEach
    void setUp() {
        plato = new Plato("plato",100,
                "plato desc", "http://logo.jpg",
                "plato categoria",false,1L);
    }

    @Test
    void guardarPlato_CuenadoRestauranteExiste_GuardarPlatoActivo() {

        when(restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())).
                thenReturn(true);
        
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
                "categoria",true,1L);

        when(platoPersistencePort.actualizarPlato(plato,1L)).thenReturn(platoActualizado);

        Plato resultado = platoUseCase.actualizarPlato(plato,1L);

        assertEquals(platoActualizado,resultado);
        verify(platoPersistencePort).actualizarPlato(plato,1L);
    }
}