package com.abcg.service;

import com.abcg.model.Order;

import java.util.List;

public interface IOrderService{
    List<Order> findAll();
    Order save(Order order);
    String generateOrderNumber();
}
