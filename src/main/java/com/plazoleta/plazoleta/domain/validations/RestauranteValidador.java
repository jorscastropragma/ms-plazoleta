package com.plazoleta.plazoleta.domain.validations;

import com.plazoleta.plazoleta.domain.exception.MensajeDomainException;
import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
import com.plazoleta.plazoleta.domain.model.Usuario;
import com.plazoleta.plazoleta.domain.spi.IUsuarioPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestauranteValidador {

    private final IUsuarioPersistencePort usuarioPersistencePort;

    public void validarNombreRestaurante(String nombreRestaurante) {
        if (nombreRestaurante == null) {
            return;
        }

        String matches = "^\\d+$";

        if(nombreRestaurante.matches(matches)) {
            //crear enums para los mensajes de error
            //excepciones mas genericas
            throw new ReglaDeNegocioInvalidaException(MensajeDomainException.NOMBRE_RESTAURANTE_INVALIDO.getMensaje());
        }
    }

    public void validarPropietarioRestaurante(long idUsuario) {
        Usuario  usuario = usuarioPersistencePort.obtenerUsuarioPorId(idUsuario);
        if (usuario == null || !usuario.getRol().equals("PROPIETARIO")) {
            throw new ReglaDeNegocioInvalidaException(MensajeDomainException.NO_ES_EL_PROPIETARIO_DEL_RESTAURANTE.getMensaje());
        }
    }
}
