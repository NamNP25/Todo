package com.example.todo.service;

import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Page<Todo> list(Long userId, String status, Pageable pageable) {
        if (status != null) {
            return todoRepository.findByUserIdAndStatus(userId, status, pageable);
        }
        return todoRepository.findByUserId(userId, pageable);
    }

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    public boolean existsById(Long id) {
        return todoRepository.existsById(id);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }
}