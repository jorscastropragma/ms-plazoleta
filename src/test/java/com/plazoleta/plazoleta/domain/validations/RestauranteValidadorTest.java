package com.plazoleta.plazoleta.domain.validations;

import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
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
    void validarNombreRestaurante_CuandoEsValido() {
        String nombreRestaurante = "Nombre del restaurante";

        assertDoesNotThrow(() -> validador.validarNombreRestaurante(nombreRestaurante));
    }

    @Test
    void validarNombreRestaurante_CuandoEsNulo() {
        String nombreRestaurante = null;

        assertDoesNotThrow(() -> validador.validarNombreRestaurante(nombreRestaurante));
    }

    @Test
    void validarNombreRestaurante_CuandoNombreNoEsValido_TieneSoloNumeros() {
        String nombreRestaurante = "3242343242";

        ReglaDeNegocioInvalidaException exception = assertThrows(ReglaDeNegocioInvalidaException.class,
                () -> validador.validarNombreRestaurante(nombreRestaurante));

        assertEquals("Nombre del restaurante invalido",exception.getMessage());
    }

    @Test
    void validarPropietarioRestaurante_CuandoEsPropietario() {
        Long idUsuario = 1L;

        when(usuarioPersistencePort.obtenerUsuarioPorId(idUsuario)).thenReturn(usuario);

        assertDoesNotThrow(() -> validador.validarPropietarioRestaurante(idUsuario));
    }

    @Test
    void validarPropietarioRestaurante_CuandoNoEsPropietario() {
        Long idUsuario  = 1L;
        usuario.setRol("ADMIN");

        when(usuarioPersistencePort.obtenerUsuarioPorId(idUsuario)).thenReturn(usuario);

        ReglaDeNegocioInvalidaException exception = assertThrows(ReglaDeNegocioInvalidaException.class, () ->
                validador.validarPropietarioRestaurante(idUsuario));

        assertEquals("No es el propietario del restaurante.",exception.getMessage());

    }
}