package com.example.picture.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //添加页面
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
    }


    //这个是spring.web.resources.static-locations的java版配置
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/pic/**").addResourceLocations("file:/D:/github/picture/src/main/resources/file/");
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }
}
