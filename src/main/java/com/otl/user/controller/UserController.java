package com.otl.user.controller;

import com.otl.user.dto.UserRegisterFormDTO;
import com.otl.user.service.UserService;
import com.otl.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        return "/pages/user/loginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/pages/user/loginForm";
    }

    @GetMapping("/register")
    public String RegisterPage(Model model) {
        //attributeName의 시작이 대문자로 되어있어서 POST의 attributeName과 달라서 계속 오류가 생겼다.
        model.addAttribute("userRegisterFormDTO", new UserRegisterFormDTO());
        return "pages/user/registerForm";
    }

    @PostMapping("/register")
    public String userRegister(@Valid UserRegisterFormDTO userRegisterFormDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRegisterFormDTO", userRegisterFormDTO);
            return "pages/user/registerForm";
        }
        try {
            User user = User.createUser(userRegisterFormDTO, passwordEncoder);
            userService.joinUser(user);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "pages/user/registerForm";
        }
        return "redirect:/user/login";
    }
}
