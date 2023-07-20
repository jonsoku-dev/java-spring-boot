package com.example.validationexample.dto;

import lombok.Data;

@Data
public class TodoDto {
    Long id;
    String title;
    Boolean completed;
}
