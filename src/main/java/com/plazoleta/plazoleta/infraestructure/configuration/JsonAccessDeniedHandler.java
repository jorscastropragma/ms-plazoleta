package com.plazoleta.plazoleta.infraestructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        int status = HttpServletResponse.SC_FORBIDDEN; // 403
        response.setStatus(status);
        response.setContentType("application/json");

        Map<String, Object> body = Map.of(
                "mensaje", "No tienes permisos para realizar esta acción.",
                "codigo",  "ACCESS_DENIED"
        );
        response.getWriter().write(mapper.writeValueAsString(body));
    }
}
