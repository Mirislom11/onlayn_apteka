package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthDTO {
    @NotEmpty(message = "login is empty or null")
    private String login;
    @NotEmpty(message = "password is empty or null")
    @Size(min = 4, max = 16, message = "password length must min 4 be max 16")
    private String password;
}
