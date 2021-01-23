package com.epam.esm.entity;

import javax.persistence.ManyToMany;
import java.util.List;

public class Order {
    private long orderId;
    @ManyToMany
    private List<GiftCertificate> giftCertificates;
}
