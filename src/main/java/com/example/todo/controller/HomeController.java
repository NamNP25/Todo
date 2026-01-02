package com.example.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String welcome() {
        // Trả về trang chào mừng có 2 nút Đăng nhập / Đăng ký
        return "forward:/welcome.html";
    }
}