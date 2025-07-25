package com.example.notificationservice.controller;


import com.example.notificationservice.dto.EmailDTO;
import com.example.notificationservice.service.EmailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


/**
 * Rest-API для отправки сообщения
 * Кушает {@link EmailDTO} в формате джейсона
 *
 * @author vmarakushin
 * @version 2.0
 */
@RestController
@RequestMapping("/api/notification")
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "200", description = "Письмо успешно отправлено.",
                        content = @Content(mediaType = "text/plain;charset=UTF-8")),
                @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера. Подробности в теле ответа.",
                        content = @Content(mediaType = "text/plain;charset=UTF-8"))
        }
)
@Tag(name= "NotificationController", description = "Отправляет письмо на email, указанный в переменной to, с содержанием, указанным в message")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "Отправить письмо")
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDTO dto) throws Exception {
            emailService.sendEmail(dto.getTo(), dto.getMessage());
            return ResponseEntity.ok("Email отправлен.");
    }
}
