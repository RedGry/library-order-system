package ru.ifmo.se.library.web.exception;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.ifmo.se.library.util.ResponseUtils;
import ru.ifmo.se.library.web.exception.model.ErrorResponse;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResponseUtils responseUtils;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException ex,
            WebRequest request
    ) {
        logger.warn("Entity not found: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        return responseUtils.buildErrorResponseWithMessage(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        logger.error("HTTP message not readable: {} - Path: {}", ex.getMessage(), request.getDescription(false), ex);
        return responseUtils.buildErrorResponseWithMessage(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        logger.warn("Validation error: {} - Path: {}", errorMessage, request.getDescription(false));
        return responseUtils.buildErrorResponseWithMessage(
                HttpStatus.BAD_REQUEST,
                errorMessage,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            WebRequest request
    ) {
        String message = String.format("Parameter '%s' should be of type %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown");

        logger.warn("Method argument type mismatch: {} - Path: {}", message, request.getDescription(false));
        return responseUtils.buildErrorResponseWithMessage(
                HttpStatus.BAD_REQUEST,
                message,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex,
            WebRequest request
    ) {
        String message = "Data integrity violation";
        if (Objects.requireNonNull(ex.getMessage()).contains("constraint")) {
            message = "Duplicate entry or constraint violation";
        }

        logger.error("Data integrity violation: {} - Path: {}", ex.getMessage(), request.getDescription(false), ex);
        return responseUtils.buildErrorResponseWithMessage(
                HttpStatus.CONFLICT,
                message,
                request.getDescription(false)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        logger.warn("Illegal argument: {} - Path: {}", ex.getMessage(), request.getDescription(false));
        return responseUtils.buildErrorResponseWithMessage(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getDescription(false)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unhandled exception: {} - Path: {}", ex.getMessage(), request.getDescription(false), ex);
        return responseUtils.buildErrorResponseWithMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getDescription(false)
        );
    }
}
