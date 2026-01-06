package com.example.todo.repository;

import com.example.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring data jpa repository cho entity User
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Thêm dòng này để tìm User theo tên đăng nhập
    Optional<User> findByUsername(String username);
}
