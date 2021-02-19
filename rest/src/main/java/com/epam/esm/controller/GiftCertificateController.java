package com.epam.esm.controller;

import com.epam.esm.controller.assembler.GiftCertificateAssembler;
import com.epam.esm.controller.assembler.TagAssembler;
import com.epam.esm.dto.GiftCertificateField;
import com.epam.esm.dto.RequestGiftCertificateDto;
import com.epam.esm.dto.ResponseGiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The {@code GiftCertificateController} class is an endpoint of the API
 * which allows its users to perform CRUD operations on gift certificates.
 * <p>
 * {@code GiftCertificateController} is accessed by sending request to /certificates
 * and the response produced by {@code GiftCertificateController} carries application/json
 * type of content (except for {@link #deleteGiftCertificateById} and {@link #deleteTagFromGiftCertificate} method,
 * which send no content back to the user).
 * <p>
 * {@code GiftCertificateController} provides the user with methods to add gift certificate ({@link #addGiftCertificate}),
 * add tag to gift certificate ({@link #addTagToGiftCertificate}), find gift certificate by id ({@link #findGiftCertificateById}),
 * find gift certificate tags ({@link #findGiftCertificateTags}), find gift certificates by parameters
 * ({@link #findGiftCertificatesByParameters}), update gift certificate ({@link #updateGiftCertificate}),
 * update one field in gift certificate ({@link #updateGiftCertificateField})
 * delete gift certificate by id ({@link #deleteGiftCertificateById}),
 * and delete tag from the gift certificate ({@link #deleteTagFromGiftCertificate}).
 *
 * @author Uladzislau Halatsevich
 * @version 2.0
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final TagAssembler tagAssembler;

    /**
     * Inserts the gift certificate passed in the request body into the storage.
     * <p>
     * Annotated with {@link PostMapping} with parameter consumes = "application/json",
     * which implies that the method processes POST requests at /certificates and that the
     * information about the new gift certificate must be carried in request body in JSON format.
     * <p>
     * The default response status is 201 - CREATED.
     *
     * @param giftCertificateDto Gift certificate to be inserted into storage. Inferred from the request body.
     * @return {@link ResponseEntity} with the inserted gift certificate and its location included.
     */
    @PostMapping
    public ResponseEntity<EntityModel<ResponseGiftCertificateDto>> addGiftCertificate
    (@RequestBody @Valid RequestGiftCertificateDto requestGiftCertificateDto) {
        ResponseGiftCertificateDto addedGiftCertificateDto = giftCertificateService.addGiftCertificate(requestGiftCertificateDto);
        return new ResponseEntity<>(giftCertificateAssembler.toModel(addedGiftCertificateDto), HttpStatus.CREATED);
    }

    /**
     * Inserts the tag passed in the request body into the storage.
     * <p>
     * Annotated with {@link PutMapping} with parameter consumes = "application/json",
     * which implies that the method processes PUT requests at /certificates/{id}/tags where id is the identifier of the
     * gift certificate where tag to be inserted. Information about the new tag must be carried in request body
     * in JSON format.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param giftCertificateId The identifier of the gift certificate to be updated. Inferred from the request URI.
     * @param tagDto            Inserted value of the tag.
     * @return {@link ResponseEntity} with the set of tags which belongs to the gift certificate.
     */
    @PutMapping("/{id}/tags")
    public ResponseEntity<CollectionModel<EntityModel<TagDto>>> addTagToGiftCertificate
    (@PathVariable("id") @Positive long giftCertificateId, @RequestBody @Valid TagDto tagDto) {
        Set<TagDto> tags = giftCertificateService.addTagToGiftCertificate(giftCertificateId, tagDto);
        return new ResponseEntity<>(tagAssembler.toCollectionModel(tags), HttpStatus.OK);
    }

    /**
     * Returns the gift certificate with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}". Therefore, processes GET requests at
     * /certificates/{id}, where id is the identifier of the requested gift certificate
     * represented by a natural number.
     * <p>
     * If there is no gift certificate with the specified id response gets status 404 - Not Found.
     * The default response status is 200 - OK.
     *
     * @param id Identifier of the requested gift certificate. Inferred from the request URI.
     * @return {@link ResponseEntity} with found gift certificate.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ResponseGiftCertificateDto>> findGiftCertificateById(@PathVariable("id")
                                                                                           @Positive long id) {
        ResponseGiftCertificateDto giftCertificate = giftCertificateService.findGiftCertificateById(id);
        return new ResponseEntity<>(giftCertificateAssembler.toModel(giftCertificate), HttpStatus.OK);
    }

    /**
     * Returns the set of tags which belongs to gift certificate with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}/tags". Therefore, processes GET requests at
     * /certificates/{id}/tags, where id is the identifier of the requested gift certificate
     * represented by a natural number.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param certificateId Identifier of the requested gift certificate. Inferred from the request URI.
     * @return {@link ResponseEntity} with the set of tags which belongs to the gift certificate.
     */
    @GetMapping("/{id}/tags")
    public ResponseEntity<CollectionModel<EntityModel<TagDto>>> findGiftCertificateTags(@PathVariable("id")
                                                                                        @Positive long certificateId) {
        Set<TagDto> tags = giftCertificateService.findGiftCertificateTags(certificateId);
        CollectionModel<EntityModel<TagDto>> collectionModel = tagAssembler.toCollectionModel(tags);
        collectionModel.forEach(tagDtoEntityModel -> tagDtoEntityModel.add(linkTo(methodOn(GiftCertificateController.class)
                .deleteTagFromGiftCertificate(certificateId, tagDtoEntityModel.getContent().getTagId()))
                .withRel("delete_tag_from_gift_certificate")));
        collectionModel.add(linkTo(methodOn(GiftCertificateController.class)
                .addTagToGiftCertificate(certificateId, null)).withRel("add_tag_to_gift_certificate"));
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    /**
     * Find gift certificates in the storage by various parameter passed as a parameter in the request URI.
     * If there is no parameters method returns all gift certificates in the storage.
     * <p>
     * Annotated by {@link GetMapping} with no parameters. Therefore, processes GET requests at /certificates.
     * <p>
     * Accepts optional request parameters {@code tagNames}, {@code name}, {@code description},
     * {@code order}, {@code page}, {@code per_page}. All parameters can be used in conjunction.
     * <p>
     * The {@code order} might contain one the following values:
     * {@code name} or {@code -name} and {@code description} or {@code -description}.
     * Minus sign indicates descending order. Default order is ascending without any signs.
     * <p>
     * The {@code page} contains number of the page. The {@code per_page} show how many elements will be displayed on the page.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param queryParameters The parameters used to find gift certificates.
     * @return {@link ResponseEntity} with the list of the gift certificates.
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ResponseGiftCertificateDto>>> findGiftCertificatesByParameters
    (@RequestParam(required = false) Map<String, String> queryParameters,
     @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
     @RequestParam(required = false, defaultValue = "10") @Positive int perPage) {
        List<ResponseGiftCertificateDto> giftCertificates = giftCertificateService
                .findGiftCertificatesByParameters(queryParameters, page, perPage);
        return new ResponseEntity<>(giftCertificateAssembler.toCollectionModel(giftCertificates), HttpStatus.OK);
    }

    /**
     * Updates the gift certificate in the storage using {@code giftCertificateDto} passed as a parameter.
     * <p>
     * Annotated with {@link PutMapping} with parameter consumes = "application/json",
     * which implies that the method processes PUT requests at /certificates/{id}, where id is the identifier of the
     * certificate to be updated represented by a natural number and that the
     * information about the updated gift certificate must be carried in request body in JSON format.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param giftCertificateDto Updated value of the gift certificate.
     * @param giftCertificateId  The identifier of the gift certificate to be updated. Inferred from the request URI.
     * @return {@link ResponseEntity} with the updated gift certificate.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ResponseGiftCertificateDto>> updateGiftCertificate
    (@PathVariable("id") @Positive long giftCertificateId, @RequestBody @Valid RequestGiftCertificateDto giftCertificateDto) {
        ResponseGiftCertificateDto updatedGiftCertificateDto = giftCertificateService
                .updateGiftCertificate(giftCertificateId, giftCertificateDto);
        return new ResponseEntity<>(giftCertificateAssembler.toModel(updatedGiftCertificateDto), HttpStatus.OK);
    }

    /**
     * Updates the field of gift certificate in the storage using {@code giftCertificateField} passed as a parameter.
     * <p>
     * Annotated with {@link PatchMapping} with parameter consumes = "application/json",
     * which implies that the method processes PATCH requests at /certificates/{id}, where id is the identifier of the
     * certificate to be updated represented by a natural number and that the
     * information about the updated gift certificate field must be carried in request body in JSON format.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param giftCertificateId    The identifier of the gift certificate to be updated. Inferred from the request URI.
     * @param giftCertificateField Updated field and value of the gift certificate.
     * @return {@link ResponseEntity} with the updated gift certificate.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<ResponseGiftCertificateDto>> updateGiftCertificateField
    (@PathVariable("id") @Positive long giftCertificateId, @RequestBody @Valid GiftCertificateField giftCertificateField) {
        ResponseGiftCertificateDto updatedGiftCertificateDto = giftCertificateService
                .updateGiftCertificateField(giftCertificateId, giftCertificateField);
        return new ResponseEntity<>(giftCertificateAssembler.toModel(updatedGiftCertificateDto), HttpStatus.OK);
    }

    /**
     * Deletes the gift certificate with the specified id from the storage.
     * <p>
     * Annotated with {@link DeleteMapping} with parameter value = "/{id}",
     * which implies that the method processes DELETE requests at
     * /certificates/{id}, where id is the identifier of the gift certificate to be deleted
     * represented by a natural number.
     * <p>
     * The default response status is 204 - No Content, as the response body is empty.
     *
     * @param id The identifier of the gift certificate to be deleted. Inferred from the request URI.
     * @return {@link ResponseEntity} with http status - 204 (NO CONTENT).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGiftCertificateById(@PathVariable("id") @Positive long id) {
        giftCertificateService.deleteGiftCertificateById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes tag from the gift certificate with the specified id from the storage.
     * <p>
     * Annotated with {@link DeleteMapping} with parameter value = "/{id}/tags/{tagId}",
     * which implies that the method processes DELETE requests at
     * /certificates/{id}/tags/{tagsId}, where id is the identifier of the gift
     * certificate where tag with tagId will be deleted.
     * <p>
     * The default response status is 204 - No Content, as the response body is empty.
     *
     * @param certificateId The identifier of the gift certificate where need to delete tag. Inferred from the request URI.
     * @param tagId         The identifier of the tag which need to delete from gift certificate. Inferred from the request URI.
     * @return {@link ResponseEntity} with http status - 204 (NO CONTENT).
     */
    @DeleteMapping("/{id}/tags/{tagId}")
    public ResponseEntity<HttpStatus> deleteTagFromGiftCertificate(@PathVariable("id") @Positive long certificateId,
                                                                   @PathVariable("tagId") @Positive long tagId) {
        giftCertificateService.deleteTagFromGiftCertificate(certificateId, tagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}