package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;

import java.util.List;

public interface OrderService {

    OrderDto makeOrder(long userId, List<Long> giftCertificateIds);

    TagDto mostWidelyUsedTagWithHighestCostOfAllOrders();
}