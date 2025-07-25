package com.example.apigateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/users")
    public Mono<String> usersFallback() {
        return Mono.just("Users service is currently unavailable. Please try later.");
    }

    @RequestMapping("/fallback/notification")
    public Mono<String> notificationFallback() {
        return Mono.just("Notification service is currently unavailable. Please try later.");
    }
}