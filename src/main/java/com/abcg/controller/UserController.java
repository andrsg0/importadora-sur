package com.abcg.controller;

import com.abcg.model.User;
import com.abcg.service.IUserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

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

}
