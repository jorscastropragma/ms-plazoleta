package com.plazoleta.plazoleta.infraestructure.input.rest;

import com.plazoleta.plazoleta.aplication.dto.*;
import com.plazoleta.plazoleta.aplication.handler.IPlatoHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Actualizar plato",
    description = "Actualizar plato, pero solamente precio y descripcion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlatoResponse.class))),
            @ApiResponse(responseCode = "400",content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PlatoResponse> actualizarPlato(@PathVariable Long id, @Valid @RequestBody PlatoPrecioDescripcionRequest platoRequest){
        return ResponseEntity.ok(iPlatoHandler.actualizarPlato(platoRequest,id));
    }

    @Operation(summary = "Cambiar estado plato",
            description = "Activar o desactivar plato, solo el propietario puede hacerlo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlatoResponse.class))),
            @ApiResponse(responseCode = "400",content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<PlatoResponse> cambiarEstadoPlato(@PathVariable Long id, @Valid @RequestBody PlatoEstadoRequest platoRequest){
        return ResponseEntity.ok(iPlatoHandler.cambiarEstadoPlato(platoRequest,id));
    }

    @Operation(summary = "Listar todos los platos de un restaurante",
            description = "Listar todos los plataos de un restaurante. paginable con filtro por categoria")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PlatoCategoriaResponse.class)
                            ))
            }
    )
    @GetMapping("/restaurante/{idRestaurante}")
    public Page<PlatoCategoriaResponse> listarPlatos(@PathVariable Long idRestaurante,
                                                     @RequestParam(required = false) Long idCategoria ,
                                                     @PageableDefault(sort = "nombre",direction = Sort.Direction.ASC) Pageable pageable){
        return iPlatoHandler.obtenerPlatos(pageable,idRestaurante,idCategoria);
    }

}
