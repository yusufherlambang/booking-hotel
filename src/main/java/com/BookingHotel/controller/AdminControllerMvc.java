package com.BookingHotel.controller;

import com.BookingHotel.dto.admins.AdminGridDTO;
import com.BookingHotel.dto.admins.AdminInsertDTO;
import com.BookingHotel.dto.admins.AdminUpdateDTO;
import com.BookingHotel.entity.Admin;
import com.BookingHotel.service.AdminService;
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

@Controller
@RequestMapping("/admin")
public class AdminControllerMvc {

    @Autowired
    private AdminService adminService;

    @GetMapping("/index")
    public String adminIndex(@RequestParam(defaultValue = "1") Integer page,
                             Model model){

        Pageable pageable = PageRequest.of(page -1, 2, Sort.by("id"));

        Page<AdminGridDTO> adminGrid = adminService.getAllAdmin(pageable);

        model.addAttribute("adminGrid", adminGrid.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", adminGrid.getTotalPages());
        model.addAttribute("breadCrumbs", "Super Admin");

        return "admin/admin-index";
    }

    @GetMapping("/insertForm")
    public String insertAdminForm(Model model){

        AdminInsertDTO adminInsertDTO = new AdminInsertDTO();

        model.addAttribute("admin", adminInsertDTO);
        model.addAttribute("type", "insert");
        model.addAttribute("breadCrumbs","Super Admin ");

        return "admin/admin-insert-form";
    }

    @PostMapping("/insert")
    public String insertAdmin(@Valid @ModelAttribute("admin") AdminInsertDTO adminInsertDTO,
                              BindingResult bindingResult,
                              Model model){

        if(bindingResult.hasErrors()) {

            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs","Super Admin ");

            return "admin/admin-insert-form";
        } else {

            adminService.insertNewAdmin(adminInsertDTO);

            return "redirect:/admin/index";
        }
    }

    @GetMapping("/delete")
    public String deleteAdmin(@RequestParam(required = true) Long id,
                              Model model){

        Admin admin = adminService.findAdminById(id);

        if (admin == null){
            model.addAttribute("breadCrumbs", "Fail to Delete");

            return "notFound/delete-id-notFound";
        } else {

            adminService.deleteAdminById(id);

            return "redirect:/admin/index";
        }
    }

    @GetMapping("/changeMyProfile")
    public String changeMyProfileForm(HttpServletRequest request
            , Model model){

        String username = request.getUserPrincipal().getName();


//        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        AdminUpdateDTO adminUpdateDTO = adminService.getAdminByUsername(username);

        model.addAttribute("admin", adminUpdateDTO);
        model.addAttribute("type", "update");
        model.addAttribute("breadCrumbs", "Update Admin " + username);

        return "admin/admin-update-form";
    }

    @PostMapping("/update")
    public String updateCustomer(@Valid @ModelAttribute("admin") AdminUpdateDTO adminUpdateDTO,
                                 BindingResult bindingResult,
                                 HttpServletRequest request,
                                 Model model){

        String username = request.getUserPrincipal().getName();


        if(bindingResult.hasErrors()){
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "Update Admin " + username);

            return "admin/admin-update-form";
        }else{

            adminService.updateAdminByUserName(adminUpdateDTO, username);
            return "redirect:/";
        }
    }
}
