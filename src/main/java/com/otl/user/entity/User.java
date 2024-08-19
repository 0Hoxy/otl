package com.otl.user.entity;

import com.otl.common.Entity.BaseEntity;
import com.otl.user.constant.Role;
import com.otl.user.dto.UserRegisterFormDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@Builder
@DynamicUpdate // Entity update시, 원하는 데이터만 update하기 위함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "provider", nullable = false)
    private String provider; // 사용자가 로그인한 서비스(ex) google, naver..)


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
    // 사용자의 이름이나 이메일을 업데이트하는 메소드
    public User updateUser(String username, String email) {
        this.name = username;
        this.email = email;

        return this;
    }
}
