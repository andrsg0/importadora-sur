package com.abcg.controller;

import com.abcg.model.Order;
import com.abcg.model.OrderDetail;
import com.abcg.model.Product;
import com.abcg.service.ProductService;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductService productService;

    // Store order details
    List<OrderDetail> details = new ArrayList<OrderDetail>();

    Order order = new Order();


    @GetMapping("")
    public String home(Model model){
        model.addAttribute("products", productService.findAll());
        return "user/home";
    }

    @GetMapping("producthome/{id}")
    public String productHome(@PathVariable Integer id, Model model){
        log.info("Id producto enviado como parametro");
        Product product = new Product();
        Optional<Product> productOptional = productService.get(id);
        product = productOptional.get();
        model.addAttribute("product", product);

        return "user/producthome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer quantity, Model model){
        OrderDetail orderDetail = new OrderDetail();
        Product product = new Product();

        double total = 0;

        Optional<Product> optionalProduct  = productService.get(id);

        log.info("Id producto añadido: {}", optionalProduct.get());
        log.info("Cantdad: {}", quantity);

        product = optionalProduct.get();
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setName(product.getName());
        orderDetail.setTotal(product.getPrice()*quantity);
        orderDetail.setProduct(product);

        //Validar que el producto no se añada 2 veces
        Integer idProduct = product.getId();
        boolean exists = details.stream().anyMatch(p -> p.getProduct().getId()==idProduct);

        if(!exists){
            details.add(orderDetail);
        }

        total = details.stream().mapToDouble(dt -> dt.getTotal()).sum();

        order.setTotal(total);
        model.addAttribute("cart", details);
        model.addAttribute("order", order);

        return "user/cart";
    }

    //Quitar un producto del carrito
    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable Integer id, Model model){
        List<OrderDetail> newOrders = new ArrayList<OrderDetail>();

        for(OrderDetail orderDetail : details){
            if(orderDetail.getProduct().getId()!=id){
                newOrders.add(orderDetail);
            }
        }

        details=newOrders;

        double total = 0;

        total = details.stream().mapToDouble(dt -> dt.getTotal()).sum();

        order.setTotal(total);
        model.addAttribute("cart", details);
        model.addAttribute("order", order);

        return "user/cart";
    }

    @GetMapping("/getCart")
    public String getCart(Model model){
        model.addAttribute("cart", details);
        model.addAttribute("order", order);
        return "/user/cart";
    }
}
