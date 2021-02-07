package com.epam.esm.dao.impl;

import com.epam.esm.dao.JPQLQuery;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.util.QueryManager;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String PAGE = "page";
    private static final String PER_PAGE = "per_page";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> findAllByParameters(Map<String, String> queryParameters) {
        int page = Integer.parseInt(queryParameters.get(PAGE));
        int perPage = Integer.parseInt(queryParameters.get(PER_PAGE));
        int firstResult = page == 1 ? 0 : page * perPage - perPage;
        String query = QueryManager.createQueryForTags(queryParameters);
        return entityManager.createQuery(JPQLQuery.SELECT_ALL_TAGS + query, Tag.class)
                .setFirstResult(firstResult).setMaxResults(perPage).getResultList();
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery(JPQLQuery.SELECT_ALL_TAGS, Tag.class).getResultList();
    }

    @Override
    public long add(Tag entity) {
        entityManager.persist(entity);
        return entity.getTagId();
    }

    @Override
    public void removeById(long id) {
        entityManager.createQuery(JPQLQuery.DELETE_TAG_BY_ID).setParameter(1, id).executeUpdate();
    }

    @Override
    public Tag update(Tag entity) {
        throw new UnsupportedOperationException("Update is not available action for Tag");
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        Tag foundTag = entityManager.createQuery(JPQLQuery.SELECT_TAG_BY_NAME, Tag.class)
                .setParameter(1, name)
                .getSingleResult();
        return Optional.ofNullable(foundTag);
    }
}