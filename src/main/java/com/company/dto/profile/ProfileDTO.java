package com.company.dto.profile;

import com.company.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private int id;
    @NotEmpty(message = "name can not be empty or null")
    private String name;
    @NotEmpty(message = "surname can not be empty or null")
    private String surname;
    @NotEmpty(message = "login can not be empty or null")
    private String login;
    @NotEmpty(message = "password can not be null or empty")
    private String password;
    @NotEmpty(message = "email can not be null or empty")
    private String email;
    private ProfileRole role;
    @NotEmpty(message = "phone can not be null or empty")
    private String phone;
    private String createdDateTime;
    private String jwt;
}
