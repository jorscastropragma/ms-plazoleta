package com.plazoleta.plazoleta.infraestructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private static final String MENSAJE_TOKEN_EXPIRADO = "El token ha expirado. Inicia sesión nuevamente.";
    private static final String CODIGO_TOKEN_EXPIRADO = "TOKEN_EXPIRADO";
    private static final String MENSAJE_TOKEN_INVALIDO = "El token es invalido. Inicia sesión nuevamente.";
    private static final String CODIGO_TOKEN_INVALIDO = "TOKEN_INVALIDO";
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        String username = null;
        try {
            username = jwtService.obtnerCorreo(token);
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            tokenInvalido(response, MENSAJE_TOKEN_EXPIRADO, CODIGO_TOKEN_EXPIRADO);
            return;
        }catch (JwtException e){
            tokenInvalido(response, MENSAJE_TOKEN_INVALIDO, CODIGO_TOKEN_INVALIDO);
            return;
        }

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + jwtService.obtenerRole(token))
        );

        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null &&
                    Boolean.TRUE.equals(jwtService.validarToken(token))){

            UsernamePasswordAuthenticationToken autenticacion =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(autenticacion);

        }
        filterChain.doFilter(request, response);
    }

    private void tokenInvalido(HttpServletResponse response,
                               String mensaje, String codigo) throws IOException {
        SecurityContextHolder.clearContext();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");

        Map<String, Object> body = Map.of(
                "mensaje", mensaje,
                "codigo",  codigo
        );
        response.getWriter().write(mapper.writeValueAsString(body));
    }
}
