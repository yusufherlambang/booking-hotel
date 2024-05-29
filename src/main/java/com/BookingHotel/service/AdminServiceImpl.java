package com.BookingHotel.service;

import com.BookingHotel.config.MvcSecurityConfig;
import com.BookingHotel.dto.admins.AdminGridDTO;
import com.BookingHotel.dto.admins.AdminInsertDTO;
import com.BookingHotel.dto.admins.AdminUpdateDTO;
import com.BookingHotel.entity.Account;
import com.BookingHotel.entity.Admin;
import com.BookingHotel.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Page<AdminGridDTO> getAllAdmin(Pageable pageable) {

        return adminRepository.getAdmins(pageable);
    }

    @Override
    public void insertNewAdmin(AdminInsertDTO adminInsertDTO) {
        // Get password encoder
        // MvcSecurityConfig.passwordEncoder(); bisa langsung dipakai karena methode passwordEncoder sudah static
        // jadi tinggal panggil methodnya dari nama classnya, tidak perlu buat object baru
        PasswordEncoder passwordEncoder = MvcSecurityConfig.passwordEncoder();

        // hashing password / encode password
        String hashPassword = passwordEncoder.encode(adminInsertDTO.getPassword());

        Account account = new Account(
                adminInsertDTO.getUsername(),
                hashPassword,
                "Admin"
        );

        Admin admin = new Admin(
                adminInsertDTO.getJobTitle()
        );

        admin.setAccount(account);

        System.out.println("new admin "+admin);

        adminRepository.save(admin);
    }

    @Override
    public void deleteAdminById(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Admin findAdminById(Long id) {

        Optional<Admin> adminOptional = adminRepository.findById(id);

        Admin admin = null;

        if (adminOptional.isPresent()){
            admin = adminOptional.get();
        }

        return admin;
    }

    @Override
    public AdminUpdateDTO getAdminByUsername(String username) {

        AdminUpdateDTO adminUpdateDTO = adminRepository.findAdminByUsername(username);

        return adminUpdateDTO;
    }

    @Override
    public void updateAdminByUserName(AdminUpdateDTO adminUpdateDTO, String username) {

        // Get password encoder
        // MvcSecurityConfig.passwordEncoder(); bisa langsung dipakai karena methode passwordEncoder sudah static
        // jadi tinggal panggil methodnya dari nama classnya, tidak perlu buat object baru
        PasswordEncoder passwordEncoder = MvcSecurityConfig.passwordEncoder();

        // hashing password / encode password
        String hashPassword = passwordEncoder.encode(adminUpdateDTO.getPassword());

        Optional<Admin> adminOptional = adminRepository.findOptionalAdminByUsername(username);

        Admin adminTemp = null;

        if (adminOptional.isPresent()){
            adminTemp = adminOptional.get();

            Account accountByUsername =  adminOptional.get().getAccount();

            accountByUsername.setPassword(hashPassword);

            adminTemp.setJobTitle(adminUpdateDTO.getJobTitle());
            adminTemp.setAccount(accountByUsername);
        }

        System.out.println("admin temp = "+adminTemp);

        adminRepository.save(adminTemp);

    }
}
