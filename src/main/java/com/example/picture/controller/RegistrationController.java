package com.example.picture.controller;

import com.example.picture.entity.User;
import com.example.picture.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepository;

    public RegistrationController(UserRepository userRepo){
        this.userRepository = userRepo;
    }

    @GetMapping
    public String registerForm(){
        return "registration";
    }

    @PostMapping
    public String processRegistration(HttpServletRequest request, HttpServletResponse response){
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setPassword(request.getParameter("password"));
        //用户注册时间和id是自动生成的
        userRepository.save(user);
        return "redirect:/login";
    }
}
