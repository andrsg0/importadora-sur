package com.abcg.service;

import com.abcg.model.Order;
import com.abcg.model.User;

import java.util.List;
import java.util.Optional;

public interface IOrderService{
    List<Order> findAll();
    Optional<Order> findById(Integer id);
    Order save(Order order);
    String generateOrderNumber();
    List<Order> findByUser(User user);
}
