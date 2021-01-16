package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQuery;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper giftCertificateMapper;
    private final TagMapper tagMapper;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public GiftCertificate add(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SqlQuery.INSERT_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setBigDecimal(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getDuration());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(entity.getCreatedDate()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(entity.getLastUpdateDate()));
            return preparedStatement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            entity.setId(keyHolder.getKey().longValue());
        }
        return entity;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return jdbcTemplate.query(SqlQuery.SELECT_CERTIFICATE_BY_ID, giftCertificateMapper, id).stream().findFirst();
    }

    @Override
    public void removeById(long id) {
        jdbcTemplate.update(SqlQuery.DELETE_CERTIFICATE, id);
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        jdbcTemplate.update(SqlQuery.UPDATE_CERTIFICATE, entity.getName(), entity.getDescription(), entity.getPrice(),
                entity.getDuration(), Timestamp.valueOf(entity.getCreatedDate()), Timestamp.valueOf(entity.getLastUpdateDate()),
                entity.getId());
        return entity;
    }

    @Override
    public void addRelationBetweenTagAndGiftCertificate(long tagId, long giftCertificateId) {
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SqlQuery.INSERT_RELATION_BETWEEN_TAG_AND_GIFT_CERTIFICATE);
            preparedStatement.setLong(1, giftCertificateId);
            preparedStatement.setLong(2, tagId);
            return preparedStatement;
        });
    }

    @Override
    public List<GiftCertificate> findCertificatesByQueryParameters(String query) {
        return jdbcTemplate.query(SqlQuery.SELECT_CERTIFICATES_BY_PARAMETERS + query, giftCertificateMapper);
    }

    @Override
    public Set<Tag> findGiftCertificateTags(long certificateId) {
        return new HashSet<>(jdbcTemplate.query(SqlQuery.SELECT_CERTIFICATE_TAGS, tagMapper, certificateId));
    }
}