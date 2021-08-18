package com.example.picture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    //这个就是什么都不做，所有的登录验证都交给spring security来完成
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "/login";
    }

}
