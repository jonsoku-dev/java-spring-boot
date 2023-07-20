package com.example.validationexample.controller;

import com.example.validationexample.dto.GetTodoDto;
import com.example.validationexample.entitiy.Todo;
import com.example.validationexample.form.TodoForm;
import com.example.validationexample.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping()
public class TodoController {
    // todoService를 Autowired를 사용하지 않고 생성자를 통해 주입받는다.
    // 이렇게 하면 TodoController를 테스트할 때 TodoService를 Mocking할 수 있다.
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping

    public List<Todo> getTodos() {
        return this.todoService.getTodos();
    }


    @GetMapping("{id}")
    public Todo getTodo(
            @PathVariable Long id, @Valid @ModelAttribute GetTodoDto getTodoDto
    ) {
        System.out.println(getTodoDto.getTitle());
        return this.todoService.getTodo(id);
    }

    @PostMapping
    public void createTodo(
            @Valid @RequestBody TodoForm todoForm
    ) {
        this.todoService.createTodo(todoForm);
    }
}
