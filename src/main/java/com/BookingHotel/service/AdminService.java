package com.BookingHotel.service;

import com.BookingHotel.dto.admins.AdminGridDTO;
import com.BookingHotel.dto.admins.AdminInsertDTO;
import com.BookingHotel.dto.admins.AdminUpdateDTO;
import com.BookingHotel.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    Page<AdminGridDTO> getAllAdmin(Pageable pageable);

    void insertNewAdmin(AdminInsertDTO adminInsertDTO);

    void deleteAdminById(Long id);

    Admin findAdminById(Long id);

    AdminUpdateDTO getAdminByUsername(String username);

    void updateAdminByUserName(AdminUpdateDTO adminUpdateDTO, String username);
}
