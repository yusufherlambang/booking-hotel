package com.BookingHotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountControllerMvc {

    @GetMapping("/loginForm")
    public String loginForm() {
        return "account/login-form";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "account/access-denied";
    }

}
