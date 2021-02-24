package com.epam.esm.controller.assembler;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements SimpleRepresentationModelAssembler<UserDto> {

    @Override
    public void addLinks(EntityModel<UserDto> resource) {
        resource.add(linkTo(methodOn(UserController.class).findUserById(resource.getContent().getUserId())).withSelfRel());
        resource.add(linkTo(methodOn(UserController.class).findUserOrders(resource.getContent().getUserId())).withRel("orders"));
        resource.add(linkTo(methodOn(UserController.class).makeOrder(resource.getContent().getUserId(), null)).withRel("make_order"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<UserDto>> resources) {
        UriComponentsBuilder uriComponentsBuilder = linkTo(methodOn(UserController.class)
                .findAllUsersByParameters(null))
                .toUriComponentsBuilder()
                .replaceQuery("{?first_name,last_name,login,order,page,per_page}");
        uriComponentsBuilder.encode();
        Link link = Link.of(uriComponentsBuilder.toUriString());
        resources.add(link.withRel("find_users"));
    }
}