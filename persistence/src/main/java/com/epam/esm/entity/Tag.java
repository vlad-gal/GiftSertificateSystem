package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(exclude = "tagId")
@NoArgsConstructor
public class Tag {
    private long tagId;
    private String name;
}
