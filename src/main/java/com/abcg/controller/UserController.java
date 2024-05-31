package com.abcg.controller;

import com.abcg.model.Order;
import com.abcg.model.User;
import com.abcg.service.IOrderService;
import com.abcg.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/signup")
    public String create() {
        return "/user/sign_up";
    }

    @PostMapping("/save")
    public String save(User user) {
        logger.info("Usuario registro: {}", user);
        user.setType("USER");
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/access")
    public String access(User user, HttpSession session) {
        logger.info("Accesos: {}", user);

        Optional<User> userOptional = userService.findByEmail(user.getEmail());
        //logger.info("Usuario de db: {}", userOptional.get());

        if (userOptional.isPresent()) {
            session.setAttribute("iduser", userOptional.get().getId());
            if (userOptional.get().getType().equals("ADMIN")) {
                return "redirect:/admin";
            }else{
                return "redirect:/";
            }
        }else{
            logger.info("El usuario no existe");
        }

        return "redirect:/";
    }

    @GetMapping("/purchases")
    public String getPurchases(Model model, HttpSession session) {
        model.addAttribute("session", session.getAttribute("iduser"));
        User user = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString())).get();
        List<Order> orders = orderService.findByUser(user);
        model.addAttribute("orders", orders);
        return "user/purchases";
    }
}
