package com.assignment.spring.web;

import com.assignment.spring.exception.WeatherNotFoundException;
import com.assignment.spring.web.model.ErrorResponseDto;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler(WeatherNotFoundException.class)
    public final ResponseEntity<Void> handleNotFoundException(WeatherNotFoundException ex, WebRequest request) {
        log.error("Unexpected error occurred.", ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<ErrorResponseDto> handleFeignException(FeignException ex, WebRequest request) {
        log.error("Unexpected error occurred.", ex);
        return ResponseEntity.unprocessableEntity().body(
                new ErrorResponseDto(ex.getCause() == null ? ex.getMessage() : ex.getCause().getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ErrorResponseDto> handleGeneralException(Throwable throwable, WebRequest request) {
        log.error("Unexpected error occurred.", throwable);
        return ResponseEntity.unprocessableEntity().body(
                new ErrorResponseDto(throwable.getMessage()));
    }
}
