package com.epam.esm.controller;

import com.epam.esm.controller.assembler.GiftCertificateAssembler;
import com.epam.esm.controller.assembler.OrderAssembler;
import com.epam.esm.controller.assembler.TagAssembler;
import com.epam.esm.controller.assembler.UserAssembler;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * The {@code UserController} class is an endpoint of the API
 * which allows its users to perform CRD operations on tag.
 * <p>
 * {@code TagController} is accessed by sending request to /tags
 * and the response produced by {@code TagController} carries application/json
 * type of content(except for {@link #deleteTagById} method, which send no content back to the user).
 * <p>
 * {@code TagController} provides the user with methods to add tag({@link #addTag}),
 * find tag by id({@link #findTagById}), find all tags({@link #findAllTags})
 * and delete by id({@link #deleteTagById}) tag from storage.
 *
 * @author Uladzislau Halatsevich
 * @version 1.0
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final UserAssembler userAssembler;
    private final TagAssembler tagAssembler;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final OrderAssembler orderAssembler;

    /**
     * Injects an object of a class implementing {@link TagService}.
     *
     * @param tagService An object of a class implementing {@link TagService}.
     */
    @Autowired
    public UserController(UserService userService, OrderService orderService, TagAssembler tagAssembler, UserAssembler userAssembler, GiftCertificateAssembler giftCertificateAssembler, OrderAssembler orderAssembler) {
        this.userService = userService;
        this.orderService = orderService;
        this.tagAssembler = tagAssembler;
        this.userAssembler = userAssembler;
        this.giftCertificateAssembler = giftCertificateAssembler;
        this.orderAssembler = orderAssembler;
    }

    /**
     * Returns the tag with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}". Therefore, processes GET requests at
     * /tags/id, where id is the identifier of the requested tag represented by a natural number.
     * <p>
     * If there is no tag with the specified id response gets status 404 - Not Found.
     * The default response status is 200 - OK.
     *
     * @param id Identifier of the requested tag. Inferred from the request URI.
     * @return {@link ResponseEntity} with found tag.
     */
    @GetMapping("/{id}")//+
    public ResponseEntity<EntityModel<UserDto>> findUserById(@PathVariable("id") long id) {
        UserDto userDto = userService.findUserById(id);
        return new ResponseEntity<>(userAssembler.toModel(userDto), HttpStatus.OK);
    }

    /**
     * Returns all the gift certificates in the storage.
     * <p>
     * Annotated by {@link GetMapping} with no parameters. Therefore, processes GET requests at /tags.
     * <p>
     * The default response status is 200 - OK.
     *
     * @return {@link ResponseEntity} with the list of the gift certificates.
     */
    @GetMapping//+
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> findAllUsersByParameters
    (@RequestParam(required = false) Map<String, String> queryParameters) {
        List<UserDto> allUsers = userService.findAllUsersByParameters(queryParameters);
        return new ResponseEntity<>(userAssembler.toCollectionModel(allUsers), HttpStatus.OK);
    }

    @GetMapping("/{id}/orders")//+
    public ResponseEntity<CollectionModel<EntityModel<OrderDto>>> findUserOrders(@PathVariable("id") long userId) {
        List<OrderDto> orders = userService.findUserOrders(userId);
        return new ResponseEntity<>(orderAssembler.toCollectionModel(orders), HttpStatus.OK);
    }

    @GetMapping("/{id}/orders/{orderId}")//+
    public ResponseEntity<EntityModel<OrderDto>> findUserOrder(@PathVariable("id") long userId,
                                                               @PathVariable("orderId") long orderId) {
        OrderDto order = userService.findUserOrder(userId, orderId);
        return new ResponseEntity<>(orderAssembler.toModel(order), HttpStatus.OK);
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<OrderDto> makeOrder(@PathVariable("id") long userId,
                                              @RequestBody List<Long> giftCertificateIds) {
        OrderDto order = orderService.makeOrder(userId, giftCertificateIds);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/top_tag")//+
    public ResponseEntity<EntityModel<TagDto>> mostWidelyUsedTag() {
        TagDto mostWidelyUsedTag = orderService.mostWidelyUsedTagWithHighestCostOfAllOrders();
        return new ResponseEntity<>(tagAssembler.toModel(mostWidelyUsedTag), HttpStatus.OK);
    }
}