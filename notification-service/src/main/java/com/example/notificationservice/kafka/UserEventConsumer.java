package com.example.notificationservice.kafka;

import com.example.notificationservice.executor.Executor;
import com.example.notificationservice.service.EmailService;

import com.example.userservice.kafka.UserEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * Потребитель сообщений кафки
 * Предпочитает {@link UserEvent}
 *
 * @author vmarakushin
 * @version 1.0
 */
@Component
public class UserEventConsumer {

    private final Executor executor;

    private final EmailService emailService;

    public UserEventConsumer(EmailService emailService, Executor executor) {
        this.emailService = emailService;
        this.executor = executor;
    }

    @KafkaListener(topics = "user-events", groupId = "notification-service")
    public void consume(UserEvent event) {
        String operation = event.operation();
        String email = event.email();

        switch (operation) {
            case "CREATE" -> executor.executeThrowing(emailService::sendEmail, email, "Ваш аккаунт успешно создан в UserServiceApp!");
            case "DELETE" -> executor.executeThrowing(emailService::sendEmail, email, "Ваш аккаунт удалён из UserServiceApp!");
            default -> System.out.println("Неизвестная операция: " + operation);
        }
    }
}