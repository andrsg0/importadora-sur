package com.abcg.service;

import com.abcg.model.Order;
import com.abcg.model.User;
import com.abcg.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService{

    @Autowired
    private IOrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public String generateOrderNumber(){
        int num = 0;
        String concatNumber = "";

        List<Order> orders = findAll();
        List<Integer> numbers = new ArrayList<Integer>();

        orders.stream().forEach(o -> numbers.add(Integer.parseInt(o.getNumber())));

        if(orders.isEmpty()){
            num = 1;
        }else{
            num = numbers.stream().max(Integer::compare).get();
            num++;
        }

        if(num<10){
            concatNumber = "000000000"+String.valueOf(num);
        }else if(num < 100){
            concatNumber = "00000000"+String.valueOf(num);
        }else if(num < 1000){
            concatNumber = "0000000"+String.valueOf(num);
        }else if(num < 10000){
            concatNumber = "000000"+String.valueOf(num);
        }else if(num < 100000){
            concatNumber = "00000"+String.valueOf(num);
        }
        return concatNumber;
    }

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }
}
