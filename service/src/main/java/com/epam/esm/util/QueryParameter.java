package com.epam.esm.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QueryParameter {
    private String tagName;
    private String certificateName;
    private String certificateDescription;
    private String order;
    private String direction;
}