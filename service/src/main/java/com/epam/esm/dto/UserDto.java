package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionPropertyKey;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {
    @Min(value = 1, message = ExceptionPropertyKey.INCORRECT_ID)
    @Positive(message = ExceptionPropertyKey.INCORRECT_ID)
    private long userId;
    @NotBlank(message = ExceptionPropertyKey.INCORRECT_LOGIN)
    @Pattern(regexp = "\\w{1,20}", message = ExceptionPropertyKey.INCORRECT_LOGIN)
    private String login;
    @NotBlank(message = ExceptionPropertyKey.INCORRECT_FIRST_NAME)
    @Pattern(regexp = "[A-ZА-Я][а-яa-z]{1,19}", message = ExceptionPropertyKey.INCORRECT_FIRST_NAME)
    private String firstName;
    @NotBlank(message = ExceptionPropertyKey.INCORRECT_LAST_NAME)
    @Pattern(regexp = "[A-ZА-Я][а-яa-z]{1,19}", message = ExceptionPropertyKey.INCORRECT_LAST_NAME)
    private String lastName;
}