package com.epam.esm.entity;

import com.epam.esm.constant.ColumnName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = ColumnName.TAG_TABLE)
@Data
@EqualsAndHashCode(exclude = {"tagId", "giftCertificates"})
@ToString(exclude = "giftCertificates")
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;
    @Column(name = ColumnName.TAG_NAME)
    private String name;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = ColumnName.CERTIFICATES_HAS_TAGS_TABLE,
            joinColumns = @JoinColumn(name = ColumnName.TAG_ID),
            inverseJoinColumns = @JoinColumn(name = ColumnName.CERTIFICATE_ID))
    private List<GiftCertificate> giftCertificates;
}