package com.epam.esm.entity;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.util.audit.AuditListener;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditListener.class)
@Table(name = ColumnName.ORDER_TABLE)
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private long userId;
    @Column(name = ColumnName.PURCHASE_DATE)
    private LocalDateTime purchaseDate;
    private BigDecimal cost;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = ColumnName.ORDERS_HAS_GIFT_CERTIFICATES_TABLE,
            joinColumns = @JoinColumn(name = ColumnName.ORDER_ID),
            inverseJoinColumns = @JoinColumn(name = ColumnName.CERTIFICATE_ID))
    private List<GiftCertificate> giftCertificates;
}
