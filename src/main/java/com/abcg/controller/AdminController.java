package com.abcg.controller;

import com.abcg.model.Order;
import com.abcg.model.Product;
import com.abcg.service.IOrderService;
import com.abcg.service.IProductService;
import com.abcg.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IProductService IProductService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    private Logger log = LoggerFactory.getLogger(AdminController.class);


    @GetMapping("")
    public String home(Model model){
        List<Product> products = IProductService.findAll();
        model.addAttribute("products", products);
        return "admin/home";
    }

    @GetMapping("/users")
    public String users(Model model){
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders";
    }

    @GetMapping("details/{id}")
    public String detail(Model model, @PathVariable Integer id){
        log.info("Id de la orden: {}", id);
        Order order = orderService.findById(id).get();
        model.addAttribute("details", order.getDetail());
        return "admin/orderdetail";
    }
}
