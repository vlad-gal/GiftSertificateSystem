package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.Set;

public interface TagService {

    Set<TagDto> findAllTags(int limit, int offset);

    TagDto findTagById(long id);

    TagDto addTag(TagDto tagDto);

    void deleteTagById(long id);
}