package com.otl.user.controller;

import com.otl.user.dto.UserRegisterFormDTO;
import com.otl.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String userLogin() {
        return "main";
    }

    @GetMapping("/register")
    public String userRegister(Model model) {
        model.addAttribute("UserRegisterFormDTO", new UserRegisterFormDTO());
        return "user/registerForm";
    }
}
