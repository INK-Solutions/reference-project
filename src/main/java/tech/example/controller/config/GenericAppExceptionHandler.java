
package tech.example.controller.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.example.controller.config.dto.AppExceptionDto;
import tech.example.service.GenericException;

@RestControllerAdvice
@Order(value = 100)
@Slf4j
public class GenericAppExceptionHandler {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<AppExceptionDto> handleApiKeyNotFound(GenericException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .internalServerError()
                .body(new AppExceptionDto(e.errorCode(), e.getMessage()));
    }
}