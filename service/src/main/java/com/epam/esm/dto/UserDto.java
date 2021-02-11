package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
public class UserDto extends RepresentationModel<UserDto> {
    private long userId;
    private String login;
    private String firstName;
    private String lastName;
}