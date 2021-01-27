package com.epam.esm.entity;

import com.epam.esm.dao.ColumnName;
import com.epam.esm.util.audit.AuditListener;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@EntityListeners(AuditListener.class)
@Table(name = ColumnName.CERTIFICATE_TABLE)
@Data
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

    public boolean add(Tag tag) {
        return tags.add(tag);
    }

    public boolean addAll(Collection<? extends Tag> c) {
        return tags.addAll(c);
    }
}