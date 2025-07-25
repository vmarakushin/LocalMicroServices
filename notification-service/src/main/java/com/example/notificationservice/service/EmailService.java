package com.example.notificationservice.service;

import org.springframework.stereotype.Service;


/**
 * Базовая реализация-заглушка EmailService
 *
 * @author vmarakushin
 * @version 1.0
 */
@Service
public class EmailService {
    public void sendEmail(String to, String text) throws Exception {
        System.out.println(to + ": " + text);
    }
}