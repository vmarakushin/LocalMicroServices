package com.example.notificationservice.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * Для закрепления и потому что мне понравилось
 *
 * @author vmarakushin
 * @version 1.0
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        logger.error(e.toString());
        return ResponseEntity.internalServerError().body("Ошибка при отправке email!");
    }
}
