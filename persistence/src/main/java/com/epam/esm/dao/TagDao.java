package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends BaseDao<Tag> {

    List<Tag> findAll();

    Optional<Tag> findTagByName(String name);
}