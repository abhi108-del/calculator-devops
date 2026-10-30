package com.spe.calculator;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DivisionByZeroException.class)
    public ResponseEntity<Map<String, Object>> handleDivByZero(DivisionByZeroException ex) {
        return error(ex.getMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class,
                       MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Map<String, Object>> handleBadParams(Exception ex) {
        return error("invalid or missing numeric parameters");
    }

    private ResponseEntity<Map<String, Object>> error(String message) {
        Map<String, Object> body = Map.of(
            "timestamp", Instant.now().toString(),
            "status", 400,
            "error", message
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
