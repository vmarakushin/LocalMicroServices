package com.example.notificationservice.kafka;

import com.example.notificationservice.executor.Executor;
import com.example.notificationservice.service.EmailService;
import com.example.userservice.kafka.UserEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


/**
 * Тесты потребителя сообщений Kafka
 *
 * @author vmarakushin
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class UserEventConsumerTest {

    @Mock
    private EmailService emailService;

    @Mock
    private Executor executor;

    @InjectMocks
    private UserEventConsumer consumer;


    @Test
    void testConsumeCreateEvent() {
        UserEvent event = new UserEvent("CREATE", "test@example.com");

        consumer.consume(event);

        verify(executor).executeThrowing(any(), eq("test@example.com"), eq("Ваш аккаунт успешно создан в UserServiceApp!"));
    }

    @Test
    void testConsumeDeleteEvent() {
        UserEvent event = new UserEvent("DELETE", "test@example.com");

        consumer.consume(event);

        verify(executor).executeThrowing(any(), eq("test@example.com"), eq("Ваш аккаунт удалён из UserServiceApp!"));

    }

    @Test
    void testConsumeUnknownOperation() {
        UserEvent event = new UserEvent("UPDATE", "test@example.com");

        consumer.consume(event);

        verifyNoInteractions(executor);
        verifyNoInteractions(emailService);
    }
}