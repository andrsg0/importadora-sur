package com.abcg.controller;

import com.abcg.model.Order;
import com.abcg.model.User;
import com.abcg.service.IOrderService;
import com.abcg.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

    @GetMapping("/signup")
    public String create() {
        return "/user/sign_up";
    }

    @PostMapping("/save")
    public String save(User user) {
        logger.info("Usuario registro: {}", user);
        user.setType("USER");
        user.setPassword(passEncode.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/access")
    public String access(User user, HttpSession session) {
        logger.info("Accesos: {}", user);

        Optional<User> userOptional = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString()));
        logger.info("Usuario de db: {}", userOptional.get());

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
        model.addAttribute("sessionu", session.getAttribute("iduser"));
        User user = userService.findById(Integer.parseInt(session.getAttribute("iduser").toString())).get();
        List<Order> orders = orderService.findByUser(user);
        model.addAttribute("orders", orders);
        return "user/purchases";
    }

    @GetMapping("/details/{id}")
    public String purchaseDetail(@PathVariable Integer id, HttpSession session, Model model){
        logger.info("Id de la orden: {}", id);
        Optional<Order> order = orderService.findById(id);
        model.addAttribute("details", order.get().getDetail());
        model.addAttribute("sessionu", session.getAttribute("iduser"));
        return "user/detail";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute("iduser");
        return "redirect:/";
    }
}
