package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagDaoImplTest {
    private EmbeddedDatabase dataSource;
    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql").addScript("classpath:test-data.sql").build();
        tagDao = new TagDaoImpl(new JdbcTemplate(dataSource), new TagMapper());
    }

    @AfterEach
    void tearDown() {
        dataSource.shutdown();
        tagDao = null;
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
    void whenAddCorrectTagThenShouldReturnCorrectTag() {
        Tag tag = new Tag();
        tag.setName("Drive");
        long actual = tagDao.add(tag);
        assertEquals(9, actual);
    }

    @Test
    void whenAddIncorrectTagThenShouldThrowException() {
        Tag tag = new Tag();
        tag.setName(null);
        assertThrows(DataIntegrityViolationException.class, () -> tagDao.add(tag));
    }

    @Test
    void whenRemoveByIdThenShouldListTagsLessOne() {
        List<Tag> tagList = tagDao.findAll();
        int expected = tagList.size();
        tagDao.removeById(1);
        List<Tag> tagListAfterRemove = tagDao.findAll();
        int actual = tagListAfterRemove.size();
        assertNotEquals(expected, actual);
    }

    @Test
    void whenFindTagByExistNameThenShouldReturnTrue() {
        Optional<Tag> optionalTag = tagDao.findTagByName("Поход");
        boolean condition = optionalTag.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindTagByNotExistNameThenShouldReturnFalse() {
        Optional<Tag> optionalTag = tagDao.findTagByName("Race");
        boolean condition = optionalTag.isPresent();
        assertFalse(condition);
    }
}