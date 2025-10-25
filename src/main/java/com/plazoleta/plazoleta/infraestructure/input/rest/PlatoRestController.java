package com.plazoleta.plazoleta.infraestructure.input.rest;

import com.plazoleta.plazoleta.aplication.dto.PlatoRequest;
import com.plazoleta.plazoleta.aplication.handler.IPlatoHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plato")
@RequiredArgsConstructor
@Tag(name = "Gestión de platos", description = "Operaciones para administrar los platos")
public class PlatoRestController {

    private final IPlatoHandler iPlatoHandler;
    @Operation(summary = "Crear un plato",
                description = "Crear plato que por defecto queda activo al restaurante seleccionado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Plato creado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400",
                    description = "Error en el formato de los datos",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping("/")
    public ResponseEntity<Void> guardarPlato(@Valid @RequestBody PlatoRequest platoRequest){
        iPlatoHandler.guardarPlato(platoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
