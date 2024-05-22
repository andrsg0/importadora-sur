package com.abcg.service;

import com.abcg.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findById(Integer id);
    User save(User user);
}
