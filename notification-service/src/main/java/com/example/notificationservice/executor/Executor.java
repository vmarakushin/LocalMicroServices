package com.example.notificationservice.executor;

import com.example.notificationservice.kafka.ThrowingFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Исполнитель для отлова исключений EmailService
 *
 * @author vmarakushin
 * @version 1.0
 */
@Component
public class Executor {

    private final Logger logger;

    public Executor() {
        this.logger = LoggerFactory.getLogger(Executor.class);
    }

    public void executeThrowing(ThrowingFunction<String, String> function, String email, String prompt) {
        try{
            function.apply(email, prompt);
        } catch (Exception e) {
            String message = "Ошибка при отправке email; ";
            logger.error(message, e);
        }
    }
}
