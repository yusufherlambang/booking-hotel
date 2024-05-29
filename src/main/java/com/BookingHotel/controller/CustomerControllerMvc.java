package com.BookingHotel.controller;


import com.BookingHotel.dto.customers.CustomerGridDTO;
import com.BookingHotel.dto.customers.CustomerRegisDTO;
import com.BookingHotel.dto.customers.CustomerUpdateDTO;
import com.BookingHotel.entity.Customer;
import com.BookingHotel.entity.Reservation;
import com.BookingHotel.service.CustomerService;
import com.BookingHotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerControllerMvc {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/registerForm")
    public String registerFormCustomer(Model model){

        CustomerRegisDTO customerDto = new CustomerRegisDTO();

        model.addAttribute("customer", customerDto);

        return "customer/register-form";
    }

    @PostMapping("/register")
    public String registerCustomer(@Valid @ModelAttribute("customer") CustomerRegisDTO customerRegisDTO,
                                   BindingResult bindingResult){

        if(bindingResult.hasErrors()) {

            return "customer/register-form";
        }

        customerService.registerAccountCustomer(customerRegisDTO);

        return "redirect:/loginForm";
    }

    @GetMapping("/index")
    public String allCustomer(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "") String name,
                              Model model){

        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by("id"));

        Page<CustomerGridDTO> customerGrid = customerService.getAllCustomer(pageable, name);

        model.addAttribute("customerGrid", customerGrid.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerGrid.getTotalPages());
        model.addAttribute("breadCrumbs", "Super Admin & Admin");
        model.addAttribute("name", name);


        return "customer/customer-index";
    }

    @GetMapping("/changeMyProfile")
    public String changeMyProfileForm(HttpServletRequest request
            ,Model model){

        String username = request.getUserPrincipal().getName();


//        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        CustomerUpdateDTO customerUpdateDTO = customerService.getCustomerByUsername(username);
//        customerUpdateDTO.setUsername(username);

        model.addAttribute("customer", customerUpdateDTO);
        model.addAttribute("type", "Update");
        model.addAttribute("breadCrumbs", "Update Customer " + username);

        return "customer/update-form";
    }

    @PostMapping("/update")
    public String updateCustomer(@Valid @ModelAttribute("customer") CustomerUpdateDTO customerUpdateDTO,
                                 BindingResult bindingResult,
                                 HttpServletRequest request,
                                 Model model){

        String username = request.getUserPrincipal().getName();


        if(bindingResult.hasErrors()){
            model.addAttribute("type", "Update");
            model.addAttribute("breadCrumbs", "Update Customer " + username);

            return "customer/update-form";
        }else{
            customerService.updateCustomerByUserName(customerUpdateDTO, username);
            return "redirect:/";
        }
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam(required = true) Long id,
                                 Model model){

        // cari customer by id
        Customer customer = customerService.getCustomerById(id);

        if (customer != null){

            // check apa customerId ada history tranksaksi
            Long customerId = customer.getId();

            List<Reservation> reservationList = reservationService.getReservationByCustId(customerId);

            if(reservationList.size() == 0){
                customerService.deleteCustomerById(customerId);

                return "redirect:/customer";
            }else{

                model.addAttribute("breadCrumbs", "Cannot Delete Customer");
                return "customer/cannot-delete-customer";
            }
        }else{
            model.addAttribute("breadCrumbs", "Fail to Delete Customer");
            return "notFound/delete-id-notFound";
        }
    }
}
