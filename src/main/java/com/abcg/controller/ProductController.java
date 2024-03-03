package com.abcg.controller;

import com.abcg.model.Product;
import com.abcg.model.User;
import com.abcg.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductController {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @GetMapping("")
    public String show(Model model){
        model.addAttribute("products", productService.findAll());
        return "products/show";
    }
    @GetMapping("/crear")
    public String create(){
        return "products/create";
    }

    @PostMapping("/guardar")
    public String save(Product product){
        LOGGER.info("Este es el objeto producto {}", product);
        User u = new User(1,"","","","","","","");
        product.setUser(u);
        productService.save(product);
        return "redirect:/productos";
    }
}
