package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.GeneratedKeysNotFoundException;
import com.epam.esm.util.QueryParameter;
import com.epam.esm.util.QueryParameterManager;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @PersistenceContext
    private EntityManager entityManager;

    //+
    @Override
    public long add(GiftCertificate entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    //+
    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    //++
    @Override
    public void removeById(long id) {
        entityManager.createQuery("delete from GiftCertificate where id = :id")
        .setParameter("id",id).executeUpdate();
    }

    //+
    @Override
    public GiftCertificate update(GiftCertificate entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<GiftCertificate> findCertificatesByQueryParameters(QueryParameter queryParameter) {
        String query = QueryParameterManager.createQuery(queryParameter);
        Query nativeQuery = entityManager.createNativeQuery(query);
//        nativeQuery.setFirstResult();
//        nativeQuery.setMaxResults();
//        return jdbcTemplate.query(SqlQuery.SELECT_CERTIFICATES_BY_PARAMETERS + query, giftCertificateMapper);
        throw new GeneratedKeysNotFoundException("Generated id not found");

    }
}