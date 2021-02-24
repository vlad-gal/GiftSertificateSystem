package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class AuthenticateRequestDto {
    @NotBlank
    @Pattern(regexp = "\\w{1,20}")
    private String login;
    @NotBlank
    @Pattern(regexp = "[\\S]{4,16}")
    private String password;
}
