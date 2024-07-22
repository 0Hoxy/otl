package com.otl.user.entity;

import com.otl.user.constant.Role;
import com.otl.user.dto.UserRegisterFormDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING)
    private Role role;


    public static User createUser(UserRegisterFormDTO userRegisterFormDTO, PasswordEncoder passwordEncoder) {
        //새로운 User 객체 생성
        User user = new User();
        //이름 가져오기
        user.setName(userRegisterFormDTO.getName());
        //이메일 가져오기
        user.setEmail(userRegisterFormDTO.getEmail());
        //주소 가져오기
        user.setAddress(userRegisterFormDTO.getAddress());
        //비밀번호 암호화
        String password = passwordEncoder.encode(userRegisterFormDTO.getPassword());
        //암호화 한 비밀번호 가져오기
        user.setPassword(password);
        //유저 롤 USER 만들기
        user.setRole(Role.USER);
        //user 반환
        return user;
    }
}
