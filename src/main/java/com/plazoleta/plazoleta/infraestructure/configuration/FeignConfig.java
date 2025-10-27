package com.plazoleta.plazoleta.infraestructure.configuration;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return template -> {
            String headerAutorizar = obtenerTokenDelRequest();
            if (headerAutorizar != null){
                template.header(HttpHeaders.AUTHORIZATION, headerAutorizar);
            }
        };
    }

    private String obtenerTokenDelRequest(){
        String headerAutorizar= null;
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null){
            headerAutorizar = header;
        }

        return headerAutorizar;
    }
}
