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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

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

    @GetMapping("/editar/{id}")
    public String edit(@PathVariable Integer id, Model model){
        Product product = new Product();
        Optional<Product> optionalProduct = productService.get(id);
        product = optionalProduct.get();

        LOGGER.info("Producto buscado: {}", product);

        model.addAttribute("product", product);

        return "products/edit";
    }

    @PostMapping("/actualizar")
    public String update(Product product){
        productService.update(product);
        return "redirect:/productos";
    }
    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable Integer id){
        productService.delete(id);
        return "redirect:/productos";
    }
}
