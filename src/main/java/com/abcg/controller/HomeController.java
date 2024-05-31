package com.abcg.controller;

import com.abcg.model.Order;
import com.abcg.model.OrderDetail;
import com.abcg.model.Product;
import com.abcg.model.User;
import com.abcg.service.IOrderDetailService;
import com.abcg.service.IOrderService;
import com.abcg.service.IProductService;
import com.abcg.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;


    // Store order details
    List<OrderDetail> details = new ArrayList<OrderDetail>();

    Order order = new Order();


    @GetMapping("")
    public String home(Model model, HttpSession session){
        log.info("Sesion del usuario: {}", session.getAttribute("iduser"));
        model.addAttribute("products", productService.findAll());

        //Session
        model.addAttribute("session", session.getAttribute("iduser"));
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

        //log.info("Id producto añadido: {}", optionalProduct.get());
        //log.info("Cantdad: {}", quantity);

        product = optionalProduct.get();
        orderDetail.setQuantity(quantity);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setName(product.getName());
        orderDetail.setTotal(product.getPrice()*quantity);
        orderDetail.setProduct(product);

        //Validar que el producto no se añada 2 veces
        Integer idProduct = product.getId();
        boolean exists = details.stream().anyMatch(p -> p.getProduct().getId() == idProduct);

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
    public String getCart(Model model, HttpSession session){
        model.addAttribute("cart", details);
        model.addAttribute("order", order);
        //Session
        model.addAttribute("session", session.getAttribute("iduser"));
        return "/user/cart";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session){

        User user = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString())).get();

        model.addAttribute("cart", details);
        model.addAttribute("order", order);
        model.addAttribute("user", user);

        return "user/orderresume";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session){
        Date dtCreation = new Date();
        order.setDateCreation(dtCreation);
        order.setNumber(orderService.generateOrderNumber());

        //Usuario
        User user = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString())).get();
        orderService.save(order);

        //Guardar detalles
        for(OrderDetail dt : details){
            dt.setOrder(order);
            orderDetailService.save(dt);
        }

        //Limpiar lista y orden
        order = new Order();
        details.clear();

        return "redirect:/";
    }

    @PostMapping("/search")
    public String seachProduct(@RequestParam String search, Model model){
        log.info("Nombre del producto: {}", search);
        List<Product> products = productService.findAll().stream().filter(p -> p.getName().contains(search)).collect(Collectors.toList());
        model.addAttribute("products", products);
        return "user/home";
    }
}
