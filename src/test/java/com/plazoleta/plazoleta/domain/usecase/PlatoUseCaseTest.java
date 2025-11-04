package com.plazoleta.plazoleta.domain.usecase;

import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
import com.plazoleta.plazoleta.domain.model.Categoria;
import com.plazoleta.plazoleta.domain.model.Plato;
import com.plazoleta.plazoleta.domain.spi.ICategoriaPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IPlatoPersistencePort;
import com.plazoleta.plazoleta.domain.spi.IRestaurantePersistencePort;
import com.plazoleta.plazoleta.domain.spi.ISeguridadContextPort;
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
class PlatoUseCaseTest {

    private Plato plato;
    private String emailUsuario;
    @Mock
    private IPlatoPersistencePort platoPersistencePort;
    @Mock
    IRestaurantePersistencePort restaurantePersistencePort;
    @Mock
    private ISeguridadContextPort seguiridadContextPort;
    @Mock
    private ICategoriaPersistencePort categoriaPersistencePort;

    @InjectMocks
    private PlatoUseCase platoUseCase;

    @BeforeEach
    void setUp() {
        plato = new Plato("plato",100,
                "plato desc", "http://logo.jpg",
                1L,"POSTRE",false,1L);
        String emailUsuario = "prueba@email";
    }

    @Test
    void guardarPlato_CuenadoRestauranteExiste_GuardarPlatoActivo() {


        when(restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())).
                thenReturn(true);
        when(seguiridadContextPort.esPropietarioDeRestaurante(plato.getIdRestaurante(),emailUsuario)).thenReturn(true);
        
        platoUseCase.guardarPlato(plato,emailUsuario);

        assertTrue(plato.getActivo(), "El plato debe estar activo por defecto");

        verify(restaurantePersistencePort).existeRestaurantePorId(1L);
        verify(platoPersistencePort).guardarPlato(plato);
    }

    @Test
    void guardarPlato_CuandoRestauranteNoExiste_LanzaExcepcion() {

        when(restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())).
                thenReturn(false);

        ReglaDeNegocioInvalidaException exception =
                assertThrows(ReglaDeNegocioInvalidaException.class,() ->{
            platoUseCase.guardarPlato(plato,emailUsuario);
        });

        assertEquals("Restaurante no encontrado.",exception.getMessage());

        assertFalse(plato.getActivo(),"El plato no debe estar activo");

        verify(restaurantePersistencePort).existeRestaurantePorId(1L);
        verify(platoPersistencePort,never()).guardarPlato(plato);
    }


    @Test
    void actualizarPlato_correctamente_devuelvePlatoActualizado() {
        Plato platoActualizado = new Plato("plato",100,
                "nueva descripcion","http://otro",
                1L,"POSTRE",true,1L);
        when(seguiridadContextPort.esPropietarioDePlato(plato.getIdRestaurante(),emailUsuario)).thenReturn(true);
        when(platoPersistencePort.actualizarPlato(plato,1L)).thenReturn(platoActualizado);

        Plato resultado = platoUseCase.actualizarPlato(plato,1L,emailUsuario);

        assertEquals(platoActualizado,resultado);
        verify(platoPersistencePort).actualizarPlato(plato,1L);
    }

    @Test
    void guardarPlato_NoEsElPropietario_lanzaExcepcion() {
        when(restaurantePersistencePort.existeRestaurantePorId(plato.getIdRestaurante())).
                thenReturn(true);
        when(seguiridadContextPort.esPropietarioDeRestaurante(plato.getIdRestaurante(),emailUsuario)).thenReturn(false);

        ReglaDeNegocioInvalidaException exception = assertThrows(ReglaDeNegocioInvalidaException.class, () -> {
            platoUseCase.guardarPlato(plato,emailUsuario);
        });

        assertEquals("No es el propietario del restaurante.",exception.getMessage());

        verify(seguiridadContextPort).esPropietarioDeRestaurante(1L,emailUsuario);
        verify(platoPersistencePort,never()).guardarPlato(plato);
    }

    @Test
    void actualizarPlato_NoEsElPropietario_lanzaExcepcion() {
        when(seguiridadContextPort.esPropietarioDePlato(plato.getIdRestaurante(),emailUsuario)).thenReturn(false);

        ReglaDeNegocioInvalidaException exception = assertThrows(ReglaDeNegocioInvalidaException.class, () -> {
            platoUseCase.actualizarPlato(plato,1L,emailUsuario);
        });

        assertEquals("No es el propietario del plato.",exception.getMessage());

        verify(seguiridadContextPort).esPropietarioDePlato(1L,emailUsuario);
        verify(platoPersistencePort,never()).actualizarPlato(plato,1L);
    }

    @Test
    void obtenerPlato_correctamente() {
        Categoria categoria = new Categoria(1L,"POSTRE","Plato de postre");

        List<Plato> platos = List.of(
                new Plato("Plato1", 10000, "Desc1", "url1", 1L, null, true, 1L),
                new Plato("Plato2", 15000, "Desc2", "url2", 1L, null, true, 1L)
        );

        Page<Plato> page = new PageImpl<>(platos);

        when(platoPersistencePort.obtenerPlatos(Pageable.unpaged(),1L,1L)).thenReturn(page);
        when(categoriaPersistencePort.obtenerCategoriaPorId(1L)).thenReturn(categoria);

        Page<Plato> platosResultado = platoUseCase.obtenerPlatos(Pageable.unpaged(),1L,1L);

        assertEquals(page.getTotalElements(),platosResultado.getTotalElements());
        assertEquals(page.getTotalPages(),platosResultado.getTotalPages());

        verify(platoPersistencePort).obtenerPlatos(Pageable.unpaged(),1L,1L);
        verify(categoriaPersistencePort,times(platosResultado.getSize())).obtenerCategoriaPorId(1L);
    }
}