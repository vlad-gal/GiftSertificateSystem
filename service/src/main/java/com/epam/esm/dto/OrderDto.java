package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {
    private long orderId;
    private LocalDateTime purchaseDate;
    private BigDecimal cost;
}