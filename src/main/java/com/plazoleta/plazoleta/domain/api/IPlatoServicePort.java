package com.plazoleta.plazoleta.domain.api;

import com.plazoleta.plazoleta.domain.model.Plato;

public interface IPlatoServicePort {
    void guardarPlato(Plato plato);
    Plato actualizarPlato(Plato plato, Long idPlato);
}
