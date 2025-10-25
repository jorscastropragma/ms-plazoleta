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
    void guardarPlato() {

        when(restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())).thenReturn(true);
        
        platoUseCase.guardarPlato(plato);

        assertTrue(plato.getActivo());

        verify(platoPersistencePort).guardarPlato(plato);
    }

    @Test
    void guardarPlatoRestauranteNoExiste() {

        when(restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())).thenReturn(false);

        assertThrows(RestauranteNoEncontradoException.class,() ->{
            platoUseCase.guardarPlato(plato);
        });

        assertFalse(plato.getActivo());
        verify(platoPersistencePort,never()).guardarPlato(plato);
    }


}