package com.example.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Date;


/**
 * @author vmarakushin
 * @version 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO {


    @Schema(description = "Уникальный идентификатор. " +
            "При создании пользователя должен быть == 0, " +
            "во всех остальных случаях - только положительные числа.",
            example = "101")

    private long id;
    @Schema(description = "Имя пользователя. " +
            "Только русские, английские символы, дефис и пробел",
            example = "Василий")

    private String name;
    @Schema(description = "Фамилия пользователя. " +
            "Только русские, английские символы, дефис и пробел",
            example = "Иванов")

    private String surname;
    @Schema(description = "Возраст пользователя. " +
            "Значения от 1 до 150",
            example = "33")

    private int age;
    @Schema(description = "Телефонный номер пользователя формат +11 цифр", example = "+12345678901")
    private String phone;
    @Schema(description = "Email пользователя. " +
            "только латинские буквы. Обязательно наличие @ и . " +
            "local part и 2nd domain минимум 3 буквы, " +
            "TLD минимум 2 буквы",
            example = "abc@abc.ab")
    private String email;
    @Schema(description = "Баланс пользователя", example = "100")
    private long money;
    @Schema(description = "Дата создания пользователя в базе данных. " +
            "Необязательное значение для запроса",
            example = "null")
    private Date createdAt;
}