package com.otl.user.controller;

import com.otl.user.dto.UserRegisterFormDTO;
import com.otl.user.entity.User;
import com.otl.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class UserControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User createUser(String email, String password) {
        UserRegisterFormDTO userRegisterFormDTO = new UserRegisterFormDTO();
        userRegisterFormDTO.setEmail(email);
        userRegisterFormDTO.setName("홍길동");
        userRegisterFormDTO.setAddress("부산광역시 부산진구 서면역 1번출구");
        userRegisterFormDTO.setPassword(password);
        User user = User.createUser(userRegisterFormDTO, passwordEncoder);
        return userService.joinUser(user);
    }


    //테이블명이 user여서 테스트가 진행이 되지않은 오류가 있었다. user는 SQL의 예약어이기 때문에 테이블 이름으로 사용할 때 다른 특별한 처리가 있다.
    //폴더가 바탕화면에 있어서 경로에 있는 한글을 인식하지 못해 test 자체가 진행이 안되는 오류가 있었다. 경로를 C드라이브에 IdeaProjects 안으로 옮겨주었따.
    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createUser(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/user/login")
                        .user(email).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }
}