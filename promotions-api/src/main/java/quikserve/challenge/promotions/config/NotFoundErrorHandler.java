package quikserve.challenge.promotions.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import quikserve.challenge.promotions.exception.NotFoundException;

import java.util.List;

@ControllerAdvice
public class NotFoundErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<List<String>> handleException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
