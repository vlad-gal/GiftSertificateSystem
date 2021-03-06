package com.epam.esm.entity;

import com.epam.esm.constant.ColumnName;
import com.epam.esm.audit.AuditListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@EntityListeners(AuditListener.class)
@Table(name = ColumnName.CERTIFICATE_TABLE)
@Data
@EqualsAndHashCode(exclude = "orders")
@ToString(exclude = "orders")
@NoArgsConstructor
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.CERTIFICATE_ID)
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    @Column(name = ColumnName.CREATE_DATE)
    private LocalDateTime createdDate;
    @Column(name = ColumnName.LAST_UPDATE_DATE)
    private LocalDateTime lastUpdateDate;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = ColumnName.CERTIFICATES_HAS_TAGS_TABLE,
            joinColumns = @JoinColumn(name = ColumnName.CERTIFICATE_ID),
            inverseJoinColumns = @JoinColumn(name = ColumnName.TAG_ID))
    private Set<Tag> tags;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = ColumnName.ORDERS_HAS_GIFT_CERTIFICATE_TABLE,
            joinColumns = @JoinColumn(name = ColumnName.CERTIFICATE_ID),
            inverseJoinColumns = @JoinColumn(name = ColumnName.ORDER_ID))
    private List<Order> orders;

    public boolean add(Tag tag) {
        return tags.add(tag);
    }

    public boolean addAll(Collection<? extends Tag> c) {
        return tags.addAll(c);
    }
}