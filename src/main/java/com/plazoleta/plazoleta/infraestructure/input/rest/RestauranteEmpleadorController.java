package com.plazoleta.plazoleta.infraestructure.input.rest;

import com.plazoleta.plazoleta.aplication.dto.RestauranteEmpleadoRequest;
import com.plazoleta.plazoleta.aplication.handler.IRestauranteEmpleadoHandler;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empleado/restaurante")
@RequiredArgsConstructor
@Tag(name = "Asignar empleado a restaurante", description = "Operaciones para la asignacion de empleados a restaurantes")
public class RestauranteEmpleadorController {

    private final IRestauranteEmpleadoHandler iRestauranteEmpleadoHandler;

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json"
                            )),
                    @ApiResponse(responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json"
                            ))
            }
    )
    @PostMapping("/")
    public void asignarEmpleado(@RequestBody RestauranteEmpleadoRequest restauranteEmpleadoRequest){
            iRestauranteEmpleadoHandler.guardarRestauranteEmpleado(restauranteEmpleadoRequest);
    }
}
