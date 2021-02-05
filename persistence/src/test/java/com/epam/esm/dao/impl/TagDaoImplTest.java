package com.epam.esm.dao.impl;

import com.epam.esm.config.PersistenceConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {PersistenceConfig.class})
@ActiveProfiles("test")
@Transactional
class TagDaoImplTest {
    @Autowired
    private TagDao tagDao;
    private HashMap<String, String> defaultQueryParameters;

    {
        defaultQueryParameters = new HashMap<>();
        defaultQueryParameters.put("page", "1");
        defaultQueryParameters.put("per_page", "5");
    }

    @Test
    void whenFindByExistIdThenShouldReturnTrue() {
        Optional<Tag> optionalTag = tagDao.findById(1);
        boolean condition = optionalTag.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByNotExistIdThenShouldReturnFalse() {
        Optional<Tag> optionalTag = tagDao.findById(10000);
        boolean condition = optionalTag.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenFindAllThenShouldReturnListTags() {
        List<Tag> tagList = tagDao.findAll();
        assertEquals(8, tagList.size());
    }

    @Test
    void whenFindAllByParametersThenShouldReturnListTags() {
        List<Tag> tagList = tagDao.findAllByParameters(defaultQueryParameters);
        assertEquals(5, tagList.size());
    }

    @Test
    void whenFindAllByParametersThenShouldThrowException() {
        assertThrows(NullPointerException.class, () -> tagDao.findAllByParameters(null));
    }

    @Test
    void whenAddCorrectTagThenShouldReturnTagId() {
        Tag tag = new Tag();
        tag.setName("Drive");
        long actual = tagDao.add(tag);
        assertEquals(9, actual);
    }

    @Test
    void whenAddIncorrectTagThenShouldThrowException() {
        Tag tag = new Tag();
        tag.setName(null);
        assertThrows(PersistenceException.class, () -> tagDao.add(tag));
    }

    @Test
    void whenRemoveByIdThenShouldNotFound() {
        tagDao.removeById(1);
        Optional<Tag> optionalTag = tagDao.findById(1);
        boolean condition = optionalTag.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenFindTagByExistNameThenShouldReturnTrue() {
        Optional<Tag> optionalTag = tagDao.findTagByName("Здоровье");
        boolean condition = optionalTag.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindTagByNotExistNameThenShouldThrowException() {
        assertThrows(NoResultException.class, () -> tagDao.findTagByName("Race"));
    }

    @Test
    void whenUpdateTagThenShouldThrowException() {
        assertThrows(UnsupportedOperationException.class, () -> tagDao.update(null));
    }
}