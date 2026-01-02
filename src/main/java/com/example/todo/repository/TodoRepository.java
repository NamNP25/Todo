package com.example.todo.repository;

import com.example.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Tìm Todo theo ID người dùng
    Page<Todo> findByUserId(Long userId, Pageable pageable);

    // Tìm Todo theo ID người dùng và trạng thái (nếu cần lọc)
    Page<Todo> findByUserIdAndStatus(Long userId, String status, Pageable pageable);
}