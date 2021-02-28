package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionPropertyKey;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class AuthenticateRequestDto {
    @NotBlank(message = ExceptionPropertyKey.INCORRECT_LOGIN)
    @Pattern(regexp = "\\w{1,20}", message = ExceptionPropertyKey.INCORRECT_LOGIN)
    private String login;
    @NotBlank(message = ExceptionPropertyKey.INCORRECT_PASSWORD)
    @Pattern(regexp = "[\\S]{4,16}", message = ExceptionPropertyKey.INCORRECT_PASSWORD)
    private String password;
}