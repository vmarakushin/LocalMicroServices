package com.example.notificationservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;


/**
 *
 * @author vmarakushin
 * @version 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EmailDTO {
    @Schema(description = "Адрес получателя", example = "example@example.com")
    private String to;
    @Schema(description = "Содержание письма", example = "Любое содержание")
    private String message;
}
