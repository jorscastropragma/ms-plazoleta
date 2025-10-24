package com.plazoleta.plazoleta.domain.validations;

import com.plazoleta.plazoleta.domain.exception.NoEsPropietarioException;
import com.plazoleta.plazoleta.domain.exception.NombreRestauranteInvalidoException;
import com.plazoleta.plazoleta.domain.model.Restaurante;
import com.plazoleta.plazoleta.domain.model.Usuario;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestauranteValidadorTest {
    @Mock
    private IUsuarioPersistencePort usuarioPersistencePort;

    @InjectMocks
    private RestauranteValidador validador;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario("fredy","cano"
                ,5413213L,"56454331", LocalDate.now()
                ,"test@test.com","PROPIETARIO");
    }

    @Test
    void validarNombreRestauranteSinException() {
        String nombreRestaurante = "Nombre del restaurante";

        assertDoesNotThrow(() -> validador.validarNombreRestaurante(nombreRestaurante));
    }

    @Test
    void validarNombreRestauranteConException() {
        String nombreRestaurante = "3242343242";

        assertThrows(NombreRestauranteInvalidoException.class, () ->
                validador.validarNombreRestaurante(nombreRestaurante));
    }

    @Test
    void validarPropietarioRestauranteSinException() {
        Long idUsuario = 1L;


        when(usuarioPersistencePort.obtenerUsuarioPorId(idUsuario)).thenReturn(usuario);
        assertDoesNotThrow(() -> validador.validarPropietarioRestaurante(idUsuario));
    }

    @Test
    void validarPropietarioRestauranteConException() {
        Long idUsuario  = 3L;
        usuario.setRol("ADMIN");

        when(usuarioPersistencePort.obtenerUsuarioPorId(idUsuario)).thenReturn(usuario);

        assertThrows(NoEsPropietarioException.class, () ->
                validador.validarPropietarioRestaurante(idUsuario));
    }
}