package com.plazoleta.plazoleta.infraestructure.input.rest;

import com.plazoleta.plazoleta.aplication.dto.RestauranteListResponse;
import com.plazoleta.plazoleta.aplication.dto.RestauranteRequest;
import com.plazoleta.plazoleta.aplication.handler.IRestauranteHandler;
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
@RequestMapping("/restaurante")
@RequiredArgsConstructor
@Tag(name = "Gestión de Restaurantes", description = "Operaciones para administrar restaurantes")
public class RestauranteRestController {

    private final IRestauranteHandler iRestauranteHandler;

    @Operation(summary = "Crear un restaurante",
            description = "Crear un restaurante. Solo lo podra crear un usuario propietario" +
                    "El nombre del restaurante no puede ser solo numero pero si puede contener numeros")
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
    public ResponseEntity<Void> crearRestaurante(@Valid @RequestBody RestauranteRequest restauranteRequest) {
        iRestauranteHandler.guardarRestaurante(restauranteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Listar todos los restaurantes",
            description = "Listar todos los restaurantes paginados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RestauranteListResponse.class)
                            ))
            }
    )
    @GetMapping("/listar")
    public Page<RestauranteListResponse> listarRestaurantes(@PageableDefault(sort = "nombre",direction = Sort.Direction.ASC) Pageable pageable){
        return iRestauranteHandler.obtenerRestaurantes(pageable);
    }
}
