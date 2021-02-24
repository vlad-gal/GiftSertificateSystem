package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Map;

public interface TagService {

    List<TagDto> findAllTagsByParameters(Map<String, String> queryParameters);

    TagDto findTagById(long id);

    TagDto addTag(TagDto tagDto);

    void deleteTagById(long id);
}