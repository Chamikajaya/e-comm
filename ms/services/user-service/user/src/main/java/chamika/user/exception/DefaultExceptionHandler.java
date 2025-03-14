package chamika.user.exception;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        log.error("Resource Not Found Exception !!! ", e);

        APIError apiError = new APIError(
                request.getRequestURI(),
                e.getMessage(),
                null,
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleException(MethodArgumentNotValidException e, HttpServletRequest request) {

        log.error("Method Argument not valid Exception occurred", e);

        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        APIError apiError = new APIError(
                request.getRequestURI(),
                "Validation failed",
                errors,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<APIError> handleDuplicateResourceException(
            DuplicateResourceException e,
            HttpServletRequest request
    ) {
        log.error("Duplicate Resource Exception !!! ", e);

        APIError apiError = new APIError(
                request.getRequestURI(),
                e.getMessage(),
                null,
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<APIError> handleDataIntegrityViolationException(
            DataIntegrityViolationException e,
            HttpServletRequest request) {

        log.error("DataIntegrityViolation Exception occurred", e);

        String message = "Database constraint violation";
        if (e.getCause() != null) {
            message = "A record with the same unique field already exists";
        }

        APIError apiError = new APIError(
                request.getRequestURI(),
                message,
                null,
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }


    // catch all exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleException(Exception e, HttpServletRequest request) {

        log.error("Unhandled Exception Occurred !!! ", e);

        APIError apiError = new APIError(request.getRequestURI(), e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}

