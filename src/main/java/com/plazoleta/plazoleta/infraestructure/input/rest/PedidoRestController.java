package com.plazoleta.plazoleta.infraestructure.input.rest;

import com.plazoleta.plazoleta.aplication.dto.PedidoRequest;
import com.plazoleta.plazoleta.aplication.dto.PedidosResponse;
import com.plazoleta.plazoleta.aplication.handler.IPedidoHandler;
import com.plazoleta.plazoleta.domain.model.Estado;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
@Tag(name = "Crear pedido", description = "Operaciones para la asignacion de pedidos")
public class PedidoRestController {

    private final IPedidoHandler pedidoHandler;

    @Operation(summary = "Crear un pedido",
            description = "Crear un pedido, debe especificar el restaurante, los platos escogidos y la cantidad de cada uno de esos platos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PedidoRequest.class)
                            )),
                    @ApiResponse(responseCode = "422",
                            description = "Error en validacion de los datos",
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

    @GetMapping("/lista/{id}/empleado")
    public Page<PedidosResponse> listarPedidos(@PathVariable Long id, @RequestParam("estado") Estado estado, Pageable pageable){
        return pedidoHandler.obtenerPedidos(id,estado,pageable);

    }

}
