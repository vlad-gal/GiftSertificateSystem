package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionPropertyKey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class RegistrationUserDto {
    @NotBlank
    @Pattern(regexp = "\\w{1,20}", message = ExceptionPropertyKey.INCORRECT_LOGIN)
    private String login;
    @NotBlank
    @Pattern(regexp = "[A-ZА-Я][а-яa-z]{1,19}", message = ExceptionPropertyKey.INCORRECT_USER_NAME)
    private String firstName;
    @NotBlank
    @Pattern(regexp = "[A-ZА-Я][а-яa-z]{1,19}", message = ExceptionPropertyKey.INCORRECT_USER_NAME)
    private String lastName;
    @NotBlank
    @Pattern(regexp = "[\\S]{4,16}")
    private String password;
    @NotBlank
    @Pattern(regexp = "[\\S]{4,16}")
    private String repeatedPassword;
}
