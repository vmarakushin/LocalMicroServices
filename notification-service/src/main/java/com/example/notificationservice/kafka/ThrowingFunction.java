package com.example.notificationservice.kafka;

/**
 * Функциональный интерфейс
 *
 * @param <T> обобщенный тип данных на вход
 * @param <U> обобщенный тип данных на вход
 * @author vmarakushin
 * @version 1.0
 */
@FunctionalInterface
public interface ThrowingFunction<T, U> {
    void apply(T t, U u) throws Exception;
}