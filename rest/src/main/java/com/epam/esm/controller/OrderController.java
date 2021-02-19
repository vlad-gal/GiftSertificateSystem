package com.epam.esm.controller;

import com.epam.esm.controller.assembler.GiftCertificateAssembler;
import com.epam.esm.controller.assembler.OrderAssembler;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ResponseGiftCertificateDto;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

//import com.epam.esm.dto.GiftCertificateDto;

/**
 * The {@code OrderController} class is an endpoint of the API
 * which allows its users to perform read operations on orders.
 * <p>
 * {@code OrderController} is accessed by sending request to /orders
 * and the response produced by {@code OrderController} carries application/json type of content.
 * <p>
 * {@code OrderController} provides the user with methods to find order's gift certificates by id
 * ({@link #findOrderGiftCertificates}), find order by id ({@link #findOrderById}).
 *
 * @author Uladzislau Halatsevich
 * @version 1.0
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final OrderAssembler orderAssembler;

    /**
     * Returns the list of gift certificates which belongs to order with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}/certificates". Therefore, processes GET requests at
     * /orders/{id}/certificates, where id is the identifier of the requested order represented by a natural number.
     * <p>
     * The default response status is 200 - OK.
     *
     * @param orderId Identifier of the requested order. Inferred from the request URI.
     * @return {@link ResponseEntity} with the list of gift certificates which belongs to the order.
     */
    @GetMapping("/{id}/certificates")
    public ResponseEntity<CollectionModel<EntityModel<ResponseGiftCertificateDto>>> findOrderGiftCertificates
    (@PathVariable("id") @Positive long orderId) {
        List<ResponseGiftCertificateDto> giftCertificates = orderService.findOrderGiftCertificates(orderId);
        return new ResponseEntity<>(giftCertificateAssembler.toCollectionModel(giftCertificates), HttpStatus.OK);
    }

    /**
     * Returns the order with the specified identifier from the storage.
     * <p>
     * Annotated by {@link GetMapping} with parameter value = "/{id}". Therefore, processes GET requests at
     * /orders/{id}, where id is the identifier of the requested order represented by a natural number.
     * <p>
     * If there is no order with the specified id response gets status 404 - Not Found.
     * The default response status is 200 - OK.
     *
     * @param orderId Identifier of the requested order. Inferred from the request URI.
     * @return {@link ResponseEntity} with found order.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDto>> findOrderById(@PathVariable("id") @Positive long orderId) {
        OrderDto order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(orderAssembler.toModel(order), HttpStatus.OK);
    }
}