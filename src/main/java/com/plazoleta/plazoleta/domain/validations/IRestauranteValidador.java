package com.plazoleta.plazoleta.domain.validations;

public interface IRestauranteValidador {
    void validarNombreRestaurante(String nombreRestaurante);
    void validarPropietarioRestaurante(long idUsuario);
}
