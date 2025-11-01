package com.plazoleta.plazoleta.infraestructure.input.rest;

import com.plazoleta.plazoleta.aplication.dto.PedidoPlatosRequest;
import com.plazoleta.plazoleta.aplication.dto.PedidoRequest;
import com.plazoleta.plazoleta.aplication.dto.RestauranteRequest;
import com.plazoleta.plazoleta.aplication.handler.IPedidoHandler;
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
@RequestMapping("/pedido")
@RequiredArgsConstructor
@Tag(name = "Crear pedido", description = "Operaciones para la asignacion de pedidos")
public class PedidoRestController {

    private final IPedidoHandler pedidoHandler;

    @Operation(summary = "Crear un pedido",
            description = "Crear un pedido")
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
    public ResponseEntity<Void> crearPedido(@Valid @RequestBody PedidoRequest pedidoRequest) {
        pedidoHandler.guardarPedido(pedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
