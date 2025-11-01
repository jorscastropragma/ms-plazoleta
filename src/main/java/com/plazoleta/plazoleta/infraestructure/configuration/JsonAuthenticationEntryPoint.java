package com.plazoleta.plazoleta.infraestructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();
    private static final String MENSAJE = "Token inválido o ausente.";
    private static final String CODIGO = "TOKEN_INVALIDO_O_AUSENTE";

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        int status = HttpServletResponse.SC_FORBIDDEN;
        response.setStatus(status);
        response.setContentType("application/json");

        Map<String, Object> body = Map.of(
                "mensaje", MENSAJE,
                "codigo",  CODIGO
        );
        response.getWriter().write(mapper.writeValueAsString(body));
    }
}
