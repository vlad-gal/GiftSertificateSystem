package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.QueryManager;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String PAGE = "page";
    private static final String PER_PAGE = "per_page";
    private static final String SELECT_ALL_CERTIFICATES = "SELECT DISTINCT g FROM GiftCertificate g ";
    private static final String DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM GiftCertificate WHERE id = ?1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long add(GiftCertificate entity) {
        entityManager.persist(entity);
        return entity.getId();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public void removeById(long id) {
        entityManager.createQuery(DELETE_GIFT_CERTIFICATE_BY_ID)
                .setParameter(1, id).executeUpdate();
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        GiftCertificate updatedGiftCertificate = entityManager.merge(entity);
        entityManager.flush();
        entityManager.clear();
        return updatedGiftCertificate;
    }

    @Override
    public List<GiftCertificate> findAllByParameters(Map<String, String> queryParameters) {
        int page = Integer.parseInt(queryParameters.get(PAGE));
        int perPage = Integer.parseInt(queryParameters.get(PER_PAGE));
        int firstResult = page == 1 ? 0 : page * perPage - perPage;
        String query = QueryManager.createQueryForCertificates(queryParameters);
        return entityManager.createQuery(SELECT_ALL_CERTIFICATES + query, GiftCertificate.class)
                .setFirstResult(firstResult).setMaxResults(perPage).getResultList();
    }
}