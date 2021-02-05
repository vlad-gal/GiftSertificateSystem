package com.epam.esm.dao.impl;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PersistenceConfig.class})
@ActiveProfiles("test")
@Transactional
class GiftCertificateDaoImplTest {
    @Autowired
    private GiftCertificateDao giftCertificateDao;
    private HashMap<String, String> defaultQueryParameters;

    {
        defaultQueryParameters = new HashMap<>();
        defaultQueryParameters.put("page", "1");
        defaultQueryParameters.put("per_page", "2");
    }

    @Test
    void whenAddCorrectGiftCertificateThenShouldReturnGiftCertificateId() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123.0"));
        long actual = giftCertificateDao.add(giftCertificate);
        assertEquals(6, actual);
    }

    @Test
    void whenAddIncorrectGiftCertificateThenShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(null);
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123.0"));
        assertThrows(PersistenceException.class, () -> giftCertificateDao.add(giftCertificate));
    }

    @Test
    void whenFindByExistIdThenShouldReturnTrue() {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(1);
        boolean condition = giftCertificateOptional.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByNotExistIdThenShouldReturnFalse() {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(100100);
        boolean condition = giftCertificateOptional.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenRemoveByExistIdThenShouldNotFound() {
        giftCertificateDao.removeById(1);
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(1);
        boolean condition = giftCertificateOptional.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenUpdateGiftCertificateThenShouldReturnUpdatedGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("SPA");
        giftCertificate.setDescription("SPA for you");
        giftCertificate.setPrice(new BigDecimal("123.0"));
        giftCertificate.setCreatedDate(LocalDateTime.now());
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(giftCertificate);
        giftCertificate.setLastUpdateDate(updatedGiftCertificate.getLastUpdateDate());
        assertEquals(giftCertificate, updatedGiftCertificate);
    }

    @Test
    void whenUpdateIncorrectGiftCertificateThenShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName(null);
        giftCertificate.setPrice(new BigDecimal("123.0"));
        assertThrows(PersistenceException.class, () -> giftCertificateDao.update(giftCertificate));
    }

    @Test
    void whenFindCertificatesByQueryParametersThenShouldReturnListCertificates() {
        List<GiftCertificate> allCertificates = giftCertificateDao.findAllByParameters(defaultQueryParameters);
        assertEquals(2, allCertificates.size());
    }

    @Test
    void whenFindCertificatesByQueryParametersThenShouldThrowException() {
        assertThrows(NullPointerException.class, () -> giftCertificateDao.findAllByParameters(null));
    }
}