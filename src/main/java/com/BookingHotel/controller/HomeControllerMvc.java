package com.BookingHotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeControllerMvc {

    @GetMapping("/index")
    public String hotelHomepage(Model model){

        model.addAttribute("breadCrumbs", "Home");
        return "home/homepage";
    }
}
