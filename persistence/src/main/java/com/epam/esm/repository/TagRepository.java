package com.epam.esm.repository;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>, QuerydslPredicateExecutor<Tag> {
    Optional<Tag> findTagByName(String name);
}
