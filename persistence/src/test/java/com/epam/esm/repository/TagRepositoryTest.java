package com.epam.esm.repository;

import com.epam.esm.config.TestConfig;
import com.epam.esm.entity.QTag;
import com.epam.esm.entity.Tag;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
@ActiveProfiles("test")
@Transactional
class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    void whenFindByExistIdThenShouldReturnTrue() {
        Optional<Tag> optionalTag = tagRepository.findById(1L);
        boolean condition = optionalTag.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenFindByNotExistIdThenShouldReturnFalse() {
        Optional<Tag> optionalTag = tagRepository.findById(10000L);
        boolean condition = optionalTag.isPresent();
        assertFalse(condition);
    }

    @Test
    void whenFindAllThenShouldReturnListOfTags() {
        List<Tag> tags = tagRepository.findAll();
        assertEquals(8, tags.size());
    }

    @Test
    void whenFindAllWithPageableThenShouldReturnPageOfTags() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<Tag> tags = tagRepository.findAll(pageable);
        assertEquals(2, tags.getContent().size());
    }

    @Test
    void whenFindAllWithPredicateThenShouldReturnPageOfTags() {
        Predicate predicate = ExpressionUtils.allOf(QTag.tag.name.startsWith("От"));
        Pageable pageable = PageRequest.of(0, 2);
        Page<Tag> tags = tagRepository.findAll(predicate, pageable);
        assertEquals(2, tags.getContent().size());
    }

    @Test
    void whenFindTagByExistNameThenShouldReturnTrue() {
        Optional<Tag> optionalTag = tagRepository.findTagByName("Здоровье");
        boolean condition = optionalTag.isPresent();
        assertTrue(condition);
    }

    @Test
    void whenSaveCorrectTagThenShouldReturnTag() {
        Tag expected = new Tag();
        expected.setName("Drive");
        Tag actual = tagRepository.save(expected);
        assertEquals(expected, actual);
    }

    @Test
    void whenDeleteByIdThenShouldNotFound() {
        tagRepository.deleteById(1L);
        Optional<Tag> optionalTag = tagRepository.findById(1L);
        boolean condition = optionalTag.isPresent();
        assertFalse(condition);
    }
}