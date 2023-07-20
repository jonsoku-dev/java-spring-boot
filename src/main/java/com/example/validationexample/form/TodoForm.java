package com.example.validationexample.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TodoForm {
    @NotBlank(message = "title is required")
    private String title;
    @NotNull(message = "completed is required")
    private Boolean completed;
}
