package com.otl.user.dto;

import com.otl.user.constant.Role;
import com.otl.user.entity.User;
import lombok.Getter;

@Getter
public class UserProfile {
    private String username; // 사용자 이름
    private String provider; // 로그인한 서비스
    private String email; // 사용자의 이메일
    private Role role;

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role.USER;
    }

    // DTO 파일을 통하여 Entity를 생성하는 메소드
    public User toEntity() {
        return User.builder()
                .name(this.username)
                .email(this.email)
                .provider(this.provider)
                .role(this.role != null ? this.role : Role.USER)
                .build();
    }
}
