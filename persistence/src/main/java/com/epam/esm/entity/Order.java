package com.epam.esm.entity;

import com.epam.esm.util.ColumnName;
import com.epam.esm.util.audit.AuditListener;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditListener.class)
@Table(name = ColumnName.ORDER_TABLE)
@Data
@ToString(exclude = "user")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    @Column(name = ColumnName.PURCHASE_DATE)
    private LocalDateTime purchaseDate;
    private BigDecimal cost;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = ColumnName.ORDERS_HAS_GIFT_CERTIFICATE_TABLE,
            joinColumns = @JoinColumn(name = ColumnName.ORDER_ID),
            inverseJoinColumns = @JoinColumn(name = ColumnName.CERTIFICATE_ID))
    private List<GiftCertificate> giftCertificates;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = ColumnName.USER_ID)
    private User user;
}