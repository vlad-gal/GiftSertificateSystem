package com.epam.esm.controller;

import com.epam.esm.controller.assembler.TagAssembler;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

/**
 * The {@code TagController} class is an endpoint of the API
 * which allows its users to perform CRD operations on tag.
 * <p>
 * {@code TagController} is accessed by sending request to /tags
 * and the response produced by {@code TagController} carries application/json
 * type of content (except for {@link #deleteTagById} method, which send no content back to the user).
 * <p>
 * {@code TagController} provides the user with methods to add tag ({@link #addTag}),
 * find tag by id ({@link #findTagById}), find all tags by parameters ({@link #findAllTagsByParameters})
 * and delete by id ({@link #deleteTagById}) tag from storage.
 *
 * @author Uladzislau Halatsevich
 * @version 2.0
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final TagAssembler tagAssembler;

    /**
     * Inserts the tag passed in the request body into the storage.
     * <p>
     * Annotated with {@link PostMapping} with parameter consumes = "application/json",
     * which implies that the method processes POST requests at /tags and that the
     * information about the new tag must be carried in request body in JSON format.
     * <p>
     * The default response status is 201 - CREATED.
     *
     * @param tagDto Tag to be inserted into storage. Inferred from the request body.
     * @return {@link ResponseEntity} with the inserted tag and its location included.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('tag:create')")
    public ResponseEntity<EntityModel<TagDto>> addTag(@Valid @RequestBody TagDto tagDto) {
        TagDto addedTagDto = tagService.addTag(tagDto);
        return new ResponseEntity<>(tagAssembler.toModel(addedTagDto), HttpStatus.CREATED);
    }

    /**
     * Returns the tag with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}". Therefore, processes GET requests at
     * /tags/{id}, where id is the identifier of the requested tag represented by a natural number.
     * <p>
     * If there is no tag with the specified id response gets status 404 - Not Found.
     * The default response status is 200 - OK.
     *
     * @param id Identifier of the requested tag. Inferred from the request URI.
     * @return {@link ResponseEntity} with found tag.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('tag:read')")
    public ResponseEntity<EntityModel<TagDto>> findTagById(@PathVariable("id") @Positive long id) {
        TagDto tagDto = tagService.findTagById(id);
        return new ResponseEntity<>(tagAssembler.toModel(tagDto), HttpStatus.OK);
    }

    /**
     * Find tags in the storage by various parameter passed as a parameter in the request URI.
     * If there is no parameters method returns all tags in the storage.
     * <p>
     * Annotated by {@link GetMapping} with no parameters. Therefore, processes GET requests at /certificates.
     * <p>
     * Accepts optional request parameters {@code tagName}, {@code order}, {@code page}, {@code per_page}.
     * All parameters can be used in conjunction.
     * <p>
     * The {@code order} might contain one the following values:
     * {@code name} or {@code -name} and {@code id} or {@code -id}.
     * Minus sign indicates descending order. Default order is ascending without any signs.
     * <p>
     * The {@code page} contains number of the page. The {@code per_page} show how many elements will be displayed on the page.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param queryParameters The parameters used to find tags.
     * @return {@link ResponseEntity} with the list of the tags.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('tag:read')")
    public ResponseEntity<CollectionModel<EntityModel<TagDto>>> findAllTagsByParameters
    (@RequestParam(required = false) Map<String, String> queryParameters,
     @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
     @RequestParam(required = false, defaultValue = "10") @Positive int perPage) {
        List<TagDto> tagsDto = tagService.findAllTagsByParameters(queryParameters, page, perPage);
        return new ResponseEntity<>(tagAssembler.toCollectionModel(tagsDto), HttpStatus.OK);
    }

    /**
     * Deletes the tag with the specified id from the storage.
     * <p>
     * Annotated with{@link DeleteMapping} with parameter value = "/{id}",
     * which implies that the method processes DELETE requests at
     * /tags/{id}, where id is the identifier of the tags to be deleted
     * represented by a natural number.
     * <p>
     * The default response status is 204 - No Content, as the response body is empty.
     *
     * @param id The identifier of the tag to be deleted. Inferred from the request URI.
     * @return {@link ResponseEntity} with http status - 204 (NO CONTENT).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('tag:delete')")
    public ResponseEntity<HttpStatus> deleteTagById(@PathVariable("id") @Positive long id) {
        tagService.deleteTagById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}