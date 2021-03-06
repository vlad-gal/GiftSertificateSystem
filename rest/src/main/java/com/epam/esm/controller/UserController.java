package com.epam.esm.controller;

import com.epam.esm.controller.assembler.GiftCertificateAssembler;
import com.epam.esm.controller.assembler.TagAssembler;
import com.epam.esm.controller.assembler.UserAssembler;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ResponseGiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The {@code UserController} class is an endpoint of the API
 * which allows its users to perform read and create operations on user.
 * <p>
 * {@code UserController} is accessed by sending request to /users
 * and the response produced by {@code UserController} carries application/json type of content .
 * <p>
 * {@code UserController} provides the user with methods to find user by id ({@link #findUserById}),
 * find all users by parameters ({@link #findAllUsersByParameters}), find user's orders ({@link #findUserOrders}),
 * find user's order ({@link #findUserOrder}), make order ({@link #makeOrder}), find most widely used
 * tag ({@link #mostWidelyUsedTag}), and find user's order which contains gift certificates
 * ({@link #findUserOrderGiftCertificates}).
 *
 * @author Uladzislau Halatsevich
 * @version 2.0
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final UserAssembler userAssembler;
    private final TagAssembler tagAssembler;
    private final GiftCertificateAssembler giftCertificateAssembler;

    /**
     * Returns the user with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}". Therefore, processes GET requests at
     * /users/{id}, where id is the identifier of the requested tag represented by a natural number.
     * <p>
     * If there is no user with the specified id response gets status 404 - Not Found.
     * The default response status is 200 - OK.
     *
     * @param id Identifier of the requested user. Inferred from the request URI.
     * @return {@link ResponseEntity} with found user.
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EntityModel<UserDto>> findUserById(@PathVariable("id") @Positive long id) {
        UserDto userDto = userService.findUserById(id);
        return new ResponseEntity<>(userAssembler.toModel(userDto), HttpStatus.OK);
    }

    /**
     * Find users in the storage by various parameter passed as a parameter in the request URI.
     * If there is no parameters method returns all users in the storage.
     * <p>
     * Annotated by {@link GetMapping} with no parameters. Therefore, processes GET requests at /certificates.
     * <p>
     * Accepts optional request parameters {@code first_name}, {@code last_name}, {@code login},
     * {@code order}, {@code page}, {@code perPage}. All parameters can be used in conjunction.
     * <p>
     * The {@code order} might contain one the following values:
     * {@code firstName} or {@code -firstName} and {@code lastName} or {@code -lastName} and {@code login}
     * or {@code -login}.
     * Minus sign indicates descending order. Default order is ascending without any signs.
     * <p>
     * The {@code page} contains number of the page. The {@code perPage} show how many elements will be displayed on the page.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param queryParameters The parameters used to find users.
     * @param page            Contains number of the page.
     * @param perPage         Show how many elements will be displayed on the page.
     * @return {@link ResponseEntity} with the list of the users.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> findAllUsersByParameters
    (@RequestParam(required = false) Map<String, String> queryParameters,
     @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
     @RequestParam(required = false, defaultValue = "10") @Positive int perPage) {
        List<UserDto> allUsers = userService.findAllUsersByParameters(queryParameters, page, perPage);
        return new ResponseEntity<>(userAssembler.toCollectionModel(allUsers), HttpStatus.OK);
    }

    /**
     * Returns the list of orders which belongs to user with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}/orders". Therefore, processes GET requests at
     * /users/{id}/orders, where id is the identifier of the requested user represented by a natural number.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param userId Identifier of the requested user. Inferred from the request URI.
     * @return {@link ResponseEntity} with the list of orders which belongs to the user.
     */
    @GetMapping("/{id}/orders")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<OrderDto>> findUserOrders(@PathVariable("id") @Positive long userId) {
        List<OrderDto> orders = userService.findUserOrders(userId);
        orders.forEach(orderDto -> {
            orderDto.add(linkTo(methodOn(UserController.class).findUserOrder(userId, orderDto.getOrderId())).withSelfRel());
            orderDto.add(linkTo(methodOn(UserController.class).findUserOrderGiftCertificates(userId, orderDto.getOrderId())).withRel("gift_certificates"));
        });
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Returns the user's order with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}/orders/{orderId}". Therefore, processes GET requests at
     * /users/{id}/orders/{orderId}, where id is the identifier of the user which have order with orderId.
     * <p>
     * If there is no user or order with the specified id response gets status 404 - Not Found.
     * The default response status is 200 - OK.
     *
     * @param userId  Identifier of the requested user. Inferred from the request URI.
     * @param orderId Identifier of the requested order. Inferred from the request URI.
     * @return {@link ResponseEntity} with found order.
     */
    @GetMapping("/{id}/orders/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<OrderDto> findUserOrder(@PathVariable("id") @Positive long userId,
                                                  @PathVariable("orderId") @Positive long orderId) {
        OrderDto order = userService.findUserOrder(orderId, userId);
        order.add(linkTo(methodOn(UserController.class).findUserOrder(userId, orderId)).withSelfRel());
        order.add(linkTo(methodOn(UserController.class).findUserOrderGiftCertificates(userId, orderId)).withRel("gift_certificates"));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Inserts the gift certificate's ids passed in the request body into the storage.
     * <p>
     * Annotated with {@link PostMapping} with parameter consumes = "application/json",
     * which implies that the method processes POST requests at /users/{id}/orders where id is the identifier of the
     * user where gift certificate's ids to be inserted. Information about the new order must be carried in request body
     * in JSON format.
     * <p>
     * The default response status is 201 - CREATED.
     *
     * @param userId             Identifier of the requested user. Inferred from the request URI.
     * @param giftCertificateIds The list of gift certificate's ids which to be inserted into storage. Inferred from the request body.
     * @return {@link ResponseEntity} with the made order and its location included.
     */
    @PostMapping("/{id}/orders")
    @PreAuthorize("hasAuthority('order:create')")
    public ResponseEntity<OrderDto> makeOrder(@PathVariable("id") @Positive long userId,
                                              @RequestBody List<@Positive Long> giftCertificateIds) {
        OrderDto order = orderService.makeOrder(userId, giftCertificateIds);
        order.add(linkTo(methodOn(UserController.class).findUserOrder(userId, order.getOrderId())).withSelfRel());
        order.add(linkTo(methodOn(UserController.class).findUserOrderGiftCertificates(userId, order.getOrderId())).withRel("gift_certificates"));
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    /**
     * Returns the most widely used tag of a user with the highest cost of all orders.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/top-tag".
     * <p>
     * If there is no tag response gets status 404 - Not Found.
     * The default response status is 200 - OK.
     *
     * @return {@link ResponseEntity} with the most widely used tag of a user with the highest cost of all orders.
     */
    @GetMapping("/top-tag")
    @PreAuthorize("hasAuthority('tag:read-top')")
    public ResponseEntity<EntityModel<TagDto>> mostWidelyUsedTag() {
        TagDto mostWidelyUsedTag = orderService.mostWidelyUsedTagWithHighestCostOfAllOrders();
        return new ResponseEntity<>(tagAssembler.toModel(mostWidelyUsedTag), HttpStatus.OK);
    }

    /**
     * Returns the list of gift certificates which belongs to order with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}/orders/{orderId}/certificates".
     * Therefore, processes GET requests at /users/{id}/orders/{orderId}/certificates,
     * where id is the identifier of the user which have order with orderId.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param userId  Identifier of the requested user. Inferred from the request URI.
     * @param orderId Identifier of the requested order. Inferred from the request URI.
     * @return {@link ResponseEntity} with the list of gift certificates which belongs to the order.
     */
    @GetMapping("/{id}/orders/{orderId}/certificates")
    @PreAuthorize("hasAuthority('order:read')")
    public ResponseEntity<CollectionModel<EntityModel<ResponseGiftCertificateDto>>> findUserOrderGiftCertificates
    (@PathVariable("id") @Positive long userId, @PathVariable("orderId") @Positive long orderId) {
        List<ResponseGiftCertificateDto> giftCertificates = orderService.findUserOrderGiftCertificates(userId, orderId);
        return new ResponseEntity<>(giftCertificateAssembler.toCollectionModel(giftCertificates), HttpStatus.OK);
    }
}