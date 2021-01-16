package com.epam.esm.dao.impl;

import com.epam.esm.dao.SqlQuery;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return jdbcTemplate.query(SqlQuery.SELECT_TAG_BY_ID, tagMapper, id).stream().findFirst();
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SqlQuery.SELECT_ALL_TAGS, tagMapper);
    }

    @Override
    public Tag add(Tag entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(SqlQuery.INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            return preparedStatement;
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            entity.setTagId(keyHolder.getKey().longValue());
        }
        return entity;
    }

    @Override
    public void removeById(long id) {
        jdbcTemplate.update(SqlQuery.DELETE_TAG, id);
    }

    @Override
    public Tag update(Tag entity) {
        throw new UnsupportedOperationException("Update is not available action for Tag");
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        return jdbcTemplate.query(SqlQuery.SELECT_TAG_BY_NAME, tagMapper, name).stream().findFirst();
    }
}