package com.plazoleta.plazoleta.infraestructure.out.restconsumer.feign;

import com.plazoleta.plazoleta.infraestructure.configuration.FeignConfig;
import com.plazoleta.plazoleta.infraestructure.out.restconsumer.dto.UsuarioInfo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuario", url = "${usuario.url}", configuration = FeignConfig.class)
public interface UsuarioFeignClient {

    @GetMapping("/{idUsuario}")
    UsuarioInfo obtenerUsuarioPorId(@PathVariable Long idUsuario);
}
