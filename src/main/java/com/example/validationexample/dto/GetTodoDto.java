package com.example.validationexample.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetTodoDto {
    @NotBlank(message = "title is required")
    @Max(value = 10, message = "title must be less than 10 characters")
    private String title;
}
