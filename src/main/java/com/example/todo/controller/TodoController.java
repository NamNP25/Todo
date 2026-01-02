package com.example.todo.controller;

import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public Page<Todo> list(@RequestParam Long userId, @RequestParam(required = false) String status, Pageable pageable) {
        if (status != null) {
            return todoRepository.findByUserIdAndStatus(userId, status, pageable);
        }
        return todoRepository.findByUserId(userId, pageable);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Todo todo) {
        if (todo.getUserId() == null) {
            return ResponseEntity.badRequest().body("Lỗi: userId không được để trống!");
        }
        return new ResponseEntity<>(todoRepository.save(todo), HttpStatus.CREATED);
    }

    // Các hàm khác (getOne, update, delete) giữ nguyên như cũ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable Long id, @RequestBody Todo todoDetails) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTitle(todoDetails.getTitle());
                    todo.setDescription(todoDetails.getDescription());
                    todo.setStatus(todoDetails.getStatus());
                    todo.setDueDate(todoDetails.getDueDate()); // QUAN TRỌNG: Thêm dòng này
                    return ResponseEntity.ok(todoRepository.save(todo));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}