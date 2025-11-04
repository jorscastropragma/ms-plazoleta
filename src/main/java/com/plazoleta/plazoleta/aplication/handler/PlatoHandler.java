package com.plazoleta.plazoleta.aplication.handler;

import com.plazoleta.plazoleta.aplication.dto.*;
import com.plazoleta.plazoleta.aplication.mapper.*;
import com.plazoleta.plazoleta.domain.api.IPlatoServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoHandler implements IPlatoHandler{

    private final IPlatoServicePort platoServicePort;
    private final PlatoResponseMapper platoResponseMapper;
    private final PlatoRequestMapper platoRequestMapper;
    private final PlatoPrecioDescripcionRequestMapper platoPrecioDescripcionRequestMapper;
    private final PlatoEstadoMapper platoEstadoMapper;
    private final PlatoCategoriaMapper platoCategoriaMapper;

    @Override
    public void guardarPlato(PlatoRequest platoRequest, String emailUsuario) {
        platoServicePort.guardarPlato(platoRequestMapper.toPlato(platoRequest),emailUsuario);
    }

    @Override
    public PlatoResponse actualizarPlato(PlatoPrecioDescripcionRequest platoRequest, Long idPlato, String emailUsuario) {
        return platoResponseMapper.toPlatoResponse(
                platoServicePort.actualizarPlato(
                        platoPrecioDescripcionRequestMapper.toPlato(platoRequest),idPlato, emailUsuario));
    }

    @Override
    public PlatoResponse cambiarEstadoPlato(PlatoEstadoRequest platoRequest, Long idPlato, String emailUsuario) {
        return platoResponseMapper.toPlatoResponse(
                platoServicePort.actualizarPlato(
                        platoEstadoMapper.toPlato(platoRequest),idPlato,emailUsuario));
    }

    @Override
    public Page<PlatoCategoriaResponse> obtenerPlatos(Pageable pageable, Long idRestaurante, Long idCategoria) {
        return platoCategoriaMapper.toPagePlatoCategoriaResponse(
                platoServicePort.obtenerPlatos(
                        pageable,idRestaurante,idCategoria));
    }
}
