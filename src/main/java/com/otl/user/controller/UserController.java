package com.otl.user.controller;

import com.otl.user.dto.UserRegisterFormDTO;
import com.otl.user.service.UserService;
import com.otl.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "main";
    }

    @GetMapping("/register")
    public String RegisterPage(Model model) {
        model.addAttribute("UserRegisterFormDTO", new UserRegisterFormDTO());
        return "user/registerForm";
    }

    @PostMapping("/register")
    public String userRegister(UserRegisterFormDTO userRegisterFormDTO) {
        User user = User.createUser(userRegisterFormDTO, passwordEncoder);
        userService.joinUser(user);

        return "redirect:/";
    }
}
