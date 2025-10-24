package com.plazoleta.plazoleta.domain.spi;

import com.plazoleta.plazoleta.domain.model.Usuario;

public interface IUsuarioPersistencePort {
    Usuario obtenerUsuarioPorId(Long idUsuario);
}
