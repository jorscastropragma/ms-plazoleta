package com.plazoleta.plazoleta.infraestructure.exception;

import com.plazoleta.plazoleta.domain.exception.ReglaDeNegocioInvalidaException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MENSAJE = "mensaje";
    private static final String CODIGO = "codigo";

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

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(RecursoNoEncontradoException ex) {
        var result = new HashMap<String, String>();
        result.put(MENSAJE, ex.getMessage());
        result.put(CODIGO,CodigoException.NO_DATA_FOUND.getCodigo());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @ExceptionHandler(ReglaDeNegocioInvalidaException.class)
    public ResponseEntity<Map<String, String>> domainReglaDeNegocioException(ReglaDeNegocioInvalidaException ex) {
        var result = new HashMap<String, String>();
        result.put(MENSAJE, ex.getMessage());
        result.put(CODIGO,CodigoException.REGLAS_DE_NEGOCIO_INVALIDAS.getCodigo());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(result);
    }

    @ExceptionHandler(RestriccionRecursoYaExisteException.class)
    public ResponseEntity<Map<String, String>> domainRestricionDupliacdoException(RestriccionRecursoYaExisteException ex) {
        var result = new HashMap<String, String>();
        result.put(MENSAJE, ex.getMessage());
        result.put(CODIGO,CodigoException.RESTRICCION_RECURSO_YA_EXISTE.getCodigo());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }





}
