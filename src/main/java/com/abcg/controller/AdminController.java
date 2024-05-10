package com.abcg.controller;

import com.abcg.model.Product;
import com.abcg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IProductService IProductService;
    @GetMapping("")
    public String home(Model model){
        List<Product> products = IProductService.findAll();
        model.addAttribute("products", products);
        return "admin/home";
    }
}
