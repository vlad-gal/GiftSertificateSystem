package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;

import java.util.Optional;

public class UserDaoImpl implements UserDao {
    @Override
    public Optional<User> findById(long id) {
        return Optional.empty();
    }

    @Override
    public long add(User entity) {
        return 0;
    }

    @Override
    public void removeById(long id) {

    }

    @Override
    public User update(User entity) {
        return null;
    }
}
