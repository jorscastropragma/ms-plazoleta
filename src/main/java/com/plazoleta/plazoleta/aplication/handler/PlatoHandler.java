package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.PlatoEstadoRequest;
import com.plazoleta.plazoleta.aplication.dto.PlatoPrecioDescripcionRequest;
import com.plazoleta.plazoleta.aplication.dto.PlatoRequest;
import com.plazoleta.plazoleta.aplication.dto.PlatoResponse;
import com.plazoleta.plazoleta.aplication.mapper.PlatoEstadoMapper;
import com.plazoleta.plazoleta.aplication.mapper.PlatoPrecioDescripcionRequestMapper;
import com.plazoleta.plazoleta.aplication.mapper.PlatoRequestMapper;
import com.plazoleta.plazoleta.aplication.mapper.PlatoResponseMapper;
import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoHandler implements IPlatoHandler{

    private final IPlatoServicePort platoServicePort;
    private final PlatoResponseMapper platoResponseMapper;
    private final PlatoRequestMapper platoRequestMapper;
    private final PlatoPrecioDescripcionRequestMapper platoPrecioDescripcionRequestMapper;
    private final PlatoEstadoMapper platoEstadoMapper;

    @Override
    public void guardarPlato(PlatoRequest platoRequest) {
        platoServicePort.guardarPlato(platoRequestMapper.toPlato(platoRequest));
    }

    @Override
    public PlatoResponse actualizarPlato(PlatoPrecioDescripcionRequest platoRequest, Long id) {
        return platoResponseMapper.toPlatoResponse(
                platoServicePort.actualizarPlato(
                        platoPrecioDescripcionRequestMapper.toPlato(platoRequest),id));
    }

    @Override
    public PlatoResponse cambiarEstadoPlato(PlatoEstadoRequest platoRequest, Long idPlato) {
        return platoResponseMapper.toPlatoResponse(
                platoServicePort.actualizarPlato(
                        platoEstadoMapper.toPlato(platoRequest),idPlato));
    }
}
