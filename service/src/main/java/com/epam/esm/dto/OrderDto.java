package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {
    private long orderId;
    private LocalDateTime purchaseDate;
    private BigDecimal cost;
    @JsonBackReference
    private List<GiftCertificateDto> giftCertificates;
}