package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "gift_certificates")
@Data
@NoArgsConstructor
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificateId")
    private long id;

    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    @Basic
    @Column(name = "create_date")
    private LocalDateTime createdDate;
    @Basic
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @ManyToMany()
    private Set<Tag> tags;
}