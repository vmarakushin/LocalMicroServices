package com.example.notificationservice.controller;


import com.example.notificationservice.dto.EmailDTO;
import com.example.notificationservice.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Тесты контроллера
 *
 * @author vmarakushin
 * @version 1.0
 */
@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Проверка успешной отправки")
    public void shouldReturn200() throws Exception {
        EmailDTO dto = new EmailDTO("example@example.com", "Вы выиграли 43,975,634,346 в лотерею. Перейдите по фишинговой ссылке, чтобы забрать приз....");
        doNothing().when(emailService).sendEmail(dto.getTo(), dto.getMessage());
        String message = "Email отправлен.";

        mockMvc.perform(post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
    }

    @Test
    @DisplayName("Проверка ошибки отправки")
    public void shouldReturn500() throws Exception {
        EmailDTO dto = new EmailDTO("example@example.com", "Вы выиграли 43,975,634,346 в лотерею. Перейдите по фишинговой ссылке, чтобы забрать приз....");
        doThrow(RuntimeException.class).when(emailService).sendEmail(dto.getTo(), dto.getMessage());


        mockMvc.perform(post("/api/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Ошибка при отправке email!"));
    }
}
