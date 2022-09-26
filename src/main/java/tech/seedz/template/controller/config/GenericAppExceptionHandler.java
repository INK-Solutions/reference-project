
package tech.seedz.template.controller.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.seedz.template.controller.config.dto.AppExceptionDto;
import tech.seedz.template.service.exception.GenericAppException;

@RestControllerAdvice
@Order(value = 100)
@Slf4j
public class GenericAppExceptionHandler {

    @ExceptionHandler(GenericAppException.class)
    public ResponseEntity<AppExceptionDto> handleApiKeyNotFound(GenericAppException e) {
        log.error(e.getMessage());

        return ResponseEntity
                .internalServerError()
                .body(new AppExceptionDto(e.errorCode(), e.getMessage()));
    }
}