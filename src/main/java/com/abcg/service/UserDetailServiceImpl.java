package com.abcg.service;

import com.abcg.model.User;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    HttpSession session;

    private Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Este es el username");
        Optional<User> optionalUser = userService.findByEmail(username);
        if(optionalUser.isPresent()){
            log.info("Esto es el id del usuario: {}",  optionalUser.get().getId());
            session.setAttribute("iduser", optionalUser.get().getId());
            User user = optionalUser.get();
            return org.springframework.security.core.userdetails.User.builder().username(user.getName()).password(user.getPassword()).roles(user.getType()).build();
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
