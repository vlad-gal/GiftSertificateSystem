package com.epam.esm.dao.mapper;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class TagMapperTest {
    private TagMapper mapper = new TagMapper();
    private ResultSet resultSet = mock(ResultSet.class);

    @Test
    void whenMapRowThenShouldReturnTag() throws SQLException {
        Tag actual = mapper.mapRow(resultSet, 1);
        Tag expected = new Tag();
        assertEquals(expected, actual);
    }
}