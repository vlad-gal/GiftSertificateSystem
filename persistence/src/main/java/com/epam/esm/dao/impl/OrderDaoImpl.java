package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;

import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    @Override
    public Optional<Order> findById(long id) {
        return Optional.empty();
    }

    @Override
    public long add(Order entity) {
        return 0;
    }

    @Override
    public void removeById(long id) {

    }

    @Override
    public Order update(Order entity) {
        return null;
    }
}
