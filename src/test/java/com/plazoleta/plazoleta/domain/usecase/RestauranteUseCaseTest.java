package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.model.Usuario;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import com.plazoleta.plazoleta.domain.validations.RestauranteValidador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestauranteUseCaseTest {

    private Restaurante restaurante;

    @Mock
    private IRestaurantePersistencePort restaurantePersistencePort;

    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    private RestauranteUseCase restauranteUseCase;

    @BeforeEach
    void setUp() {
        restauranteUseCase = new RestauranteUseCase(restaurantePersistencePort, usuarioPersistencePort);
        restaurante = new Restaurante("local",
                "124545","cra 32 -58", "3125896547",
                "http://logo.jpg",1L);
    }

    @Test
    void guardarRestaurante_correctamente() {
        Usuario usuario = new Usuario("hector","perez",5464651L,"1546435",
                LocalDate.now(),"email@email.com","PROPIETARIO");

        when(usuarioPersistencePort.obtenerUsuarioPorId(1L)).thenReturn(usuario);

        restauranteUseCase.guardarRestaurante(restaurante);

        verify(restaurantePersistencePort).guardarRestaurante(restaurante);
    }

    @Test
    void guardarRestaurante_CuandoTieneSoloNumerosEnNombre() {
        restaurante.setNombre("124545");

        ReglaDeNegocioInvalidaException exception = assertThrows(ReglaDeNegocioInvalidaException.class, () -> {
            restauranteUseCase.guardarRestaurante(restaurante);
        });

        assertEquals("El nombre del restaurante es invalido.",exception.getMessage());


        verify(restaurantePersistencePort,never()).guardarRestaurante(restaurante);
    }

    @Test
    void guardarRestaurante_CuandoNoEsUnPropietario(){
        Usuario usuario = new Usuario("hector","perez",5464651L,"1546435",
                LocalDate.now(),"email@email.com","ADMINISTRADOR");

        when(usuarioPersistencePort.obtenerUsuarioPorId(1L)).thenReturn(usuario);

        ReglaDeNegocioInvalidaException exception = assertThrows(ReglaDeNegocioInvalidaException.class, () -> {
            restauranteUseCase.guardarRestaurante(restaurante);
        });

        assertEquals("No es el propietario del restaurante.",exception.getMessage());

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