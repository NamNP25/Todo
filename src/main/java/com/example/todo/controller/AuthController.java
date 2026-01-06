package com.example.todo.controller;

import com.example.todo.dtos.requestDTO.UserRequestDTO;
import com.example.todo.dtos.responseDTO.UserResponseDTO;
import com.example.todo.entity.User;
import com.example.todo.repository.UserRepository;
import com.example.todo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    public AuthController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            if (userRequestDTO.getUsername() == null || userRequestDTO.getEmail() == null || userRequestDTO.getPassword() == null) {
                return ResponseEntity.badRequest().body("Lỗi: username, email và password là bắt buộc!");
            }

            // Gọi đến UserService để xử lý logic
            UserResponseDTO register = userService.register(userRequestDTO);

            return ResponseEntity.ok(register);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    // --- THÊM HÀM LOGIN NÀY ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Kiểm tra mật khẩu
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(user); // Trả về thông tin user nếu đúng pass
            } else {
                return ResponseEntity.status(401).body("Sai mật khẩu rồi bạn ơi!");
            }
        }
        return ResponseEntity.status(404).body("Không tìm thấy người dùng này!");
    }

    /*
    {
       message: "Sai mật khẩu rồi bạn ơi!",
       data: {
//           username: "john_doe",
//           email: user1@gmail.com"
            null
       }
       responseTime: "2026-01-10T10:10:10Z"
    }

    **/


}
