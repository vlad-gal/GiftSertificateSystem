package com.epam.esm.controller.assembler;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.ResponseGiftCertificateDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateAssembler implements SimpleRepresentationModelAssembler<ResponseGiftCertificateDto> {
    @Override
    public void addLinks(EntityModel<ResponseGiftCertificateDto> resource) {
        resource.add(linkTo(methodOn(GiftCertificateController.class)
                .findGiftCertificateById(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(GiftCertificateController.class)
                .findGiftCertificateTags(resource.getContent().getId())).withRel("tags"));
        resource.add(linkTo(methodOn(GiftCertificateController.class)
                .updateGiftCertificate(resource.getContent().getId(), null)).withRel("update"));
        resource.add(linkTo(methodOn(GiftCertificateController.class)
                .updateGiftCertificateField(resource.getContent().getId(), null)).withRel("update_field"));
        resource.add(linkTo(methodOn(GiftCertificateController.class)
                .deleteGiftCertificateById(resource.getContent().getId())).withRel("delete"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ResponseGiftCertificateDto>> resources) {
        UriComponentsBuilder uriComponentsBuilder = linkTo(methodOn(GiftCertificateController.class)
                .findGiftCertificatesByParameters(null, 0, 10))
                .toUriComponentsBuilder()
                .replaceQuery("{?tagName,name,description,order,page,per_page}");
        uriComponentsBuilder.encode();
        Link link = Link.of(uriComponentsBuilder.toUriString());
        resources.add(link.withRel("find_gift_certificates"));
        resources.add(linkTo(methodOn(GiftCertificateController.class)
                .addGiftCertificate(null)).withRel("add_new_certificate"));
    }
}