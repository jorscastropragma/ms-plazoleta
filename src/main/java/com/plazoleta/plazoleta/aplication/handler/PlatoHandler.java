package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.PlatoRequest;
import com.plazoleta.plazoleta.aplication.mapper.IPlatoRequestMapper;
import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import com.plazoleta.plazoleta.domain.model.Plato;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoHandler implements IPlatoHandler{

    private final IPlatoServicePort platoServicePort;

    private final IPlatoRequestMapper platoMapper;

    @Override
    public void guardarPlato(PlatoRequest platoRequest) {
        platoServicePort.guardarPlato(platoMapper.toPlato(platoRequest));
    }
}
