package com.plazoleta.plazoleta.infraestructure.exception;

import com.plazoleta.plazoleta.domain.exception.NoEsPropietarioException;
import com.plazoleta.plazoleta.domain.exception.NombreRestauranteInvalidoException;
import com.plazoleta.plazoleta.domain.exception.RestauranteNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MENSAJE = "mensaje";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        String message = "Error de validación";
        if (ex.getBindingResult().getFieldError() != null) {
            BindingResult bindingResult = ex.getBindingResult();
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MENSAJE, message));
    }

    @ExceptionHandler({
            RestauranteNoEncontradoException.class,
            PlatoNoEncontradoException.class
    })
    public ResponseEntity<Map<String, String>> plazoletaException(RuntimeException  ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(MENSAJE,ex.getLocalizedMessage()));
    }

    @ExceptionHandler(NombreRestauranteInvalidoException.class)
    public ResponseEntity<Map<String, String>> nombreRestauranteInvalidoException(NombreRestauranteInvalidoException  ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(MENSAJE,ex.getLocalizedMessage()));
    }

    @ExceptionHandler(NoEsPropietarioException.class)
    public ResponseEntity<Map<String, String>> noEsPropietarioException(NoEsPropietarioException  ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(MENSAJE,ex.getLocalizedMessage()));
    }

}
