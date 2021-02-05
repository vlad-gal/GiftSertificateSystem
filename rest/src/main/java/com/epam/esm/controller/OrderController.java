package com.epam.esm.controller;

import com.epam.esm.controller.assembler.GiftCertificateAssembler;
import com.epam.esm.controller.assembler.OrderAssembler;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final OrderAssembler orderAssembler;

    public OrderController(OrderService orderService, GiftCertificateAssembler giftCertificateAssembler, OrderAssembler orderAssembler) {
        this.orderService = orderService;
        this.giftCertificateAssembler = giftCertificateAssembler;
        this.orderAssembler = orderAssembler;
    }

    @GetMapping("/{id}/gift_certificates")
    public ResponseEntity<CollectionModel<EntityModel<GiftCertificateDto>>> findOrderGiftCertificates(@PathVariable("id") long orderId) {
        List<GiftCertificateDto> giftCertificates = orderService.findOrderGiftCertificates(orderId);
        return new ResponseEntity<>(giftCertificateAssembler.toCollectionModel(giftCertificates), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrderDto>> findOrderById(@PathVariable("id") long orderId) {
        OrderDto order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(orderAssembler.toModel(order), HttpStatus.OK);
    }
}