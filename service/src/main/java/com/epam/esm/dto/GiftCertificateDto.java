//package com.epam.esm.dto;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import org.springframework.hateoas.RepresentationModel;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Set;
//
//@Data
//@NoArgsConstructor
//public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
//    private long id;
//    private String name;
//    private String description;
//    private BigDecimal price;
//    private int duration;
//    private LocalDateTime createdDate;
//    private LocalDateTime lastUpdateDate;
//    @JsonBackReference
//    private Set<TagDto> tags;
//}