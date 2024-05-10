package com.abcg.service;

import com.abcg.model.OrderDetail;
import com.abcg.repository.IOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailImpl implements IOrderDetailService{

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
