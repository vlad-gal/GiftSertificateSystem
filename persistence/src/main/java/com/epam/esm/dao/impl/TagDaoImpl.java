package com.epam.esm.dao.impl;

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
    private static final String SELECT_ALL_TAGS = "SELECT t FROM Tag t ";
    private static final String DELETE_TAG_BY_ID = "DELETE FROM Tag WHERE tagId = ?1";
    private static final String SELECT_TAG_BY_NAME = "SELECT t FROM Tag t WHERE t.name = ?1";

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
        return entityManager.createQuery(SELECT_ALL_TAGS + query, Tag.class)
                .setFirstResult(firstResult).setMaxResults(perPage).getResultList();
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery(SELECT_ALL_TAGS, Tag.class).getResultList();
    }

    @Override
    public long add(Tag entity) {
        entityManager.persist(entity);
        return entity.getTagId();
    }

    @Override
    public void removeById(long id) {
        entityManager.createQuery(DELETE_TAG_BY_ID).setParameter(1, id).executeUpdate();
    }

    @Override
    public Tag update(Tag entity) {
        throw new UnsupportedOperationException("Update is not available action for Tag");
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        return entityManager.createQuery(SELECT_TAG_BY_NAME, Tag.class)
                .setParameter(1, name)
                .getResultStream().findFirst();
    }
}