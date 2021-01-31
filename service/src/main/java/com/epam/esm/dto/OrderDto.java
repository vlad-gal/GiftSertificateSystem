package com.epam.esm.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {
    private long orderId;
    private LocalDateTime purchaseDate;
    private BigDecimal cost;
    private List<GiftCertificateDto> giftCertificates;
}