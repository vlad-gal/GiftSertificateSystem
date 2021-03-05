package com.epam.esm.audit;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {
    @PrePersist
    private void onPrePersist(Object object) {
        if (object instanceof GiftCertificate) {
            GiftCertificate giftCertificate = (GiftCertificate) object;
            giftCertificate.setCreatedDate(LocalDateTime.now());
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
        } else if (object instanceof Order) {
            Order order = (Order) object;
            order.setPurchaseDate(LocalDateTime.now());
        }
    }

    @PreUpdate
    private void onPreUpdate(Object object) {
        if (object instanceof GiftCertificate) {
            GiftCertificate giftCertificate = (GiftCertificate) object;
            giftCertificate.setLastUpdateDate(LocalDateTime.now());
        }
    }
}