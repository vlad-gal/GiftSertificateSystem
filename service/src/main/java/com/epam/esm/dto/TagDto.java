package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionPropertyKey;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class TagDto extends RepresentationModel<TagDto> {
    private long tagId;
    @NotBlank
    @Pattern(regexp = "[а-яА-Я\\w\\s\\d\\.?!]{1,45}", message = ExceptionPropertyKey.INCORRECT_TAG_NAME)
    private String name;
}