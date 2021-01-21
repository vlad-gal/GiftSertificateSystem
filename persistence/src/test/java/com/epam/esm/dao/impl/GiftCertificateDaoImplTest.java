package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class GiftCertificateDaoImplTest {
    private EmbeddedDatabase dataSource;
    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    void setUp() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql").addScript("classpath:test-data.sql").build();
        giftCertificateDao = new GiftCertificateDaoImpl(new JdbcTemplate(dataSource), new GiftCertificateMapper(),
                new TagMapper());
    }

    @AfterEach
    void tearDown() {
        dataSource.shutdown();
        giftCertificateDao = null;
    }

    @Test
    void whenAddCorrectGiftCertificateThenShouldReturnCorrectGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("Hello");
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123.0"));
        giftCertificate.setCreatedDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        long actual = giftCertificateDao.add(giftCertificate);
        assertEquals(6, actual);
    }

    @Test
    void whenAddIncorrectGiftCertificateThenShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(null);
        giftCertificate.setDescription("Hello from description");
        giftCertificate.setPrice(new BigDecimal("123.0"));
        giftCertificate.setCreatedDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.add(giftCertificate));
    }

    @Test
    void whenFindByExistIdThenShouldReturnTrue() {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(1);
        boolean condition = giftCertificateOptional.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByNotExistIdThenShouldReturnFalse() {
        Optional<GiftCertificate> giftCertificateOptional = giftCertificateDao.findById(1000);
        boolean condition = giftCertificateOptional.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenRemoveByExistIdThenShouldListCertificatesLessOne() {
        List<GiftCertificate> certificateList = giftCertificateDao.findCertificatesByQueryParameters("");
        int expected = certificateList.size();
        giftCertificateDao.removeById(1);
        List<GiftCertificate> certificateListAfterRemove = giftCertificateDao.findCertificatesByQueryParameters("");
        int actual = certificateListAfterRemove.size();
        assertNotEquals(expected, actual);
    }

    @Test
    void whenUpdateGiftCertificateThenShouldReturnUpdatedGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("SPA");
        giftCertificate.setPrice(new BigDecimal("123.0"));
        giftCertificate.setCreatedDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        GiftCertificate updatedGiftCertificate = giftCertificateDao.update(giftCertificate);
        assertEquals(giftCertificate, updatedGiftCertificate);
    }

    @Test
    void whenUpdateIncorrectGiftCertificateThenShouldThrowException() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName(null);
        giftCertificate.setPrice(new BigDecimal("123.0"));
        giftCertificate.setCreatedDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        assertThrows(DataIntegrityViolationException.class, () -> giftCertificateDao.update(giftCertificate));
    }

    @Test
    void whenFindCertificatesByQueryParametersThenShouldReturnListCertificates() {
        List<GiftCertificate> allCertificates = giftCertificateDao.findCertificatesByQueryParameters("");
        assertEquals(5, allCertificates.size());
    }

    @Test
    void whenFindCertificatesByQueryParametersThenShouldThrowException() {
        assertThrows(BadSqlGrammarException.class, () -> giftCertificateDao.findCertificatesByQueryParameters("SDASD"));
    }

    @Test
    void whenFindGiftCertificateTagsThenShouldReturnSetTags() {
        Set<Tag> tagSet = giftCertificateDao.findGiftCertificateTags(1);
        assertEquals(3, tagSet.size());
    }

    @Test
    void whenAddRelationBetweenTagAndGiftCertificateThenShouldNotThrowException() {
        assertDoesNotThrow(() -> giftCertificateDao.addRelationBetweenTagAndGiftCertificate(2, 2));
    }

    @Test
    void whenAddRelationBetweenTagAndGiftCertificateThenShouldThrowException() {
        assertThrows(DuplicateKeyException.class, () -> giftCertificateDao.addRelationBetweenTagAndGiftCertificate(1, 2));
    }
}