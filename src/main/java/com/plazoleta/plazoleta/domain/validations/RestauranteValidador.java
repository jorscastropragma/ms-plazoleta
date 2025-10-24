package com.plazoleta.plazoleta.domain.validations;

import com.plazoleta.plazoleta.domain.exception.NoEsPropietarioException;
import com.plazoleta.plazoleta.domain.exception.NombreRestauranteInvalidoException;
import com.plazoleta.plazoleta.domain.model.Usuario;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestauranteValidador implements IRestauranteValidador {

    private final IUsuarioPersistencePort usuarioPersistencePort;

    @Override
    public void validarNombreRestaurante(String nombreRestaurante) {
        if (nombreRestaurante == null) {
            return;
        }

        String matches = "^\\d+$";

        if(nombreRestaurante.matches(matches)) {
            throw new NombreRestauranteInvalidoException("Nombre del restaurante invalido");
        }
    }

    @Override
    public void validarPropietarioRestaurante(long idUsuario) {
        Usuario  usuario = usuarioPersistencePort.obtenerUsuarioPorId(idUsuario);
        if (usuario == null || !usuario.getRol().equals("PROPIETARIO")) {
            throw new NoEsPropietarioException("Usuario invalido.");
        }
    }
}
