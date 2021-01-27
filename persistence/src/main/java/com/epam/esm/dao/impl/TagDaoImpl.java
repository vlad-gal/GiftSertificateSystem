package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override//+
    public Optional<Tag> findById(long id) {
        return Optional.of(entityManager.find(Tag.class, id));
    }

    @Override//+
    public List<Tag> findAll(int limit, int offset) {
        return entityManager.createQuery("select t from Tag t", Tag.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Tag> findAll() {//+
        return entityManager.createQuery("select t from Tag t", Tag.class)
                .getResultList();
    }

    @Override//+
    public long add(Tag entity) {
        entityManager.persist(entity);
        return entity.getTagId();
    }

    @Override
    public void removeById(long id) {//+
        entityManager.createQuery("delete from Tag where tagId = ?1")
                .setParameter(1, id).executeUpdate();
    }

    @Override
    public Tag update(Tag entity) {
        throw new UnsupportedOperationException("Update is not available action for Tag");
    }

    @Override//+
    public Optional<Tag> findTagByName(String name) {
        Tag foundTag = entityManager.createQuery("select t from Tag t where t.name= ?1", Tag.class)
                .setParameter(1, name)
                .getSingleResult();
        return Optional.of(foundTag);
    }
}