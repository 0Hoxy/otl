package com.otl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterFormDTO {
    private String name;

    private String email;

    private String password;

    private String address;
}
