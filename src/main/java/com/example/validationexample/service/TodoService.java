package com.example.validationexample.service;

import com.example.validationexample.dto.TodoDto;
import com.example.validationexample.entitiy.Todo;
import com.example.validationexample.form.TodoForm;
import com.example.validationexample.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> getTodos() {
        return this.todoRepository.findAll();
    }

    public Todo getTodo(Long id) {
        return this.todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo가 없습니다."));
    }

    public void createTodo(TodoForm todoForm) {
        Todo todo = new Todo();
        todo.setTitle(todoForm.getTitle());
        todo.setCompleted(todoForm.getCompleted());
        this.todoRepository.save(todo);
    }

    public void updateTodo() {
    }

    public void deleteTodo() {
    }
}
