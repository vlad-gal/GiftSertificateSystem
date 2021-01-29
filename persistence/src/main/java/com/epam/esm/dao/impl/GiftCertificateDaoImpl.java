package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.JPQLQuery;
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
        entityManager.createQuery(JPQLQuery.DELETE_GIFT_CERTIFICATE_BY_ID)
                .setParameter(1, id).executeUpdate();
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        GiftCertificate merge = entityManager.merge(entity);
        entityManager.flush();
        return merge;
    }

    @Override
    public List<GiftCertificate> findCertificatesByQueryParameters(Map<String, String> queryParameter) {
        int page = Integer.parseInt(queryParameter.get(PAGE));
        int perPage = Integer.parseInt(queryParameter.get(PER_PAGE));
        int firstResult = page == 1 ? 0 : page * perPage - 2;
        String query = QueryManager.createQueryForCertificates(queryParameter);
        return entityManager.createQuery(JPQLQuery.SELECT_ALL_CERTIFICATES + query, GiftCertificate.class)
                .setFirstResult(firstResult).setMaxResults(perPage).getResultList();
    }

//    @Override
//    public List<GiftCertificate> findCertificatesByQueryParameters(QueryParameter queryParameter, int limit, int offset) {
//        String query = QueryParameterManager.createQuery(queryParameter);
//        Query nativeQuery = entityManager.createNativeQuery(query);
////        nativeQuery.setFirstResult();
////        nativeQuery.setMaxResults();
////        return jdbcTemplate.query(SqlQuery.SELECT_CERTIFICATES_BY_PARAMETERS + query, giftCertificateMapper);
//        throw new GeneratedKeysNotFoundException("Generated id not found");
//    }
}