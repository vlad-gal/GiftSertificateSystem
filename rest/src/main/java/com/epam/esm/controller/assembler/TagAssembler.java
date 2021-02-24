package com.epam.esm.controller.assembler;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagAssembler implements SimpleRepresentationModelAssembler<TagDto> {
    @Override
    public void addLinks(EntityModel<TagDto> resource) {
        resource.add(linkTo(methodOn(TagController.class).findTagById(resource.getContent().getTagId())).withSelfRel());
        resource.add(linkTo(methodOn(TagController.class).deleteTagById(resource.getContent().getTagId())).withRel("delete"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<TagDto>> resources) {
        resources.add(linkTo(methodOn(TagController.class).addTag(null)).withRel("add"));
        UriComponentsBuilder uriComponentsBuilder = linkTo(methodOn(TagController.class)
                .findAllTagsByParameters(null))
                .toUriComponentsBuilder()
                .replaceQuery("{?tagName,page,order,per_page}");
        uriComponentsBuilder.encode();
        Link link = Link.of(uriComponentsBuilder.toUriString());
        resources.add(link.withRel("find_tags"));
    }
}