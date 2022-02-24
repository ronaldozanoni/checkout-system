package qikserve.challenge.checkout.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import qikserve.challenge.checkout.exception.InvalidProductIdException;

import java.util.Map;

@ControllerAdvice
public class InvalidProductIdExceptionHandler {

    @ExceptionHandler(InvalidProductIdException.class)
    public ResponseEntity<Map<String, String>> handleException(InvalidProductIdException e) {
        return ResponseEntity.badRequest()
                .header("Content-Type", "application/json")
                .body(Map.of("error", e.getMessage()));
    }
}
