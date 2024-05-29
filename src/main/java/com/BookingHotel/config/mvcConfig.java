package com.BookingHotel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/home/index");
        registry.addViewController("/customer").setViewName("forward:/customer/index");
        registry.addViewController("/admin").setViewName("forward:/admin/index");
        registry.addViewController("/room").setViewName("forward:/room/index");
        registry.addViewController("/reservation").setViewName("forward:/reservation/all-current-reservation");
        registry.addViewController("/loginForm").setViewName("forward:/account/loginForm");
        registry.addViewController("/registerForm").setViewName("forward:/customer/registerForm");

    }
}
