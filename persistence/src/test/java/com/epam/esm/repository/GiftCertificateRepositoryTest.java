package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QGiftCertificate;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("test")
@Transactional
class GiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    void whenSaveCorrectGiftCertificateThenShouldReturnGiftCertificate() {
        GiftCertificate expected = new GiftCertificate();
        expected.setName("Hello");
        expected.setDescription("Hello from description");
        expected.setPrice(new BigDecimal("123.0"));
        GiftCertificate actual = giftCertificateRepository.save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void whenAddIncorrectGiftCertificateThenShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(null);
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123.0"));
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateRepository.save(giftCertificate));
    }

    @Test
    void whenFindByExistIdThenShouldReturnTrue() {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository.findById(1L);
        boolean condition = giftCertificateOptional.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByNotExistIdThenShouldReturnFalse() {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository.findById(100100L);
        boolean condition = giftCertificateOptional.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenDeleteByExistIdThenShouldNotFound() {
        giftCertificateRepository.deleteById(1L);
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateRepository.findById(1L);
        boolean condition = giftCertificateOptional.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenFindAllThenShouldReturnListCertificates() {
        List<GiftCertificate> allCertificates = giftCertificateRepository.findAll();
        assertEquals(5, allCertificates.size());
    }

    @Test
    void whenFindAllWithPageableThenShouldReturnPageOfCertificates() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<GiftCertificate> certificates = giftCertificateRepository.findAll(pageable);
        assertEquals(2, certificates.getContent().size());
    }

    @Test
    void whenFindAllWithPredicateThenShouldReturnPageOfCertificates() {
        Predicate predicate = ExpressionUtils.allOf(QGiftCertificate.giftCertificate.name.startsWith("П"));
        Pageable pageable = PageRequest.of(0, 2);
        Page<GiftCertificate> certificates = giftCertificateRepository.findAll(predicate, pageable);
        assertEquals(2, certificates.getContent().size());
    }
}