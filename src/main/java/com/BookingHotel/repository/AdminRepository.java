package com.BookingHotel.repository;

import com.BookingHotel.dto.admins.AdminGridDTO;
import com.BookingHotel.dto.admins.AdminUpdateDTO;
import com.BookingHotel.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query("""
            SELECT new com.BookingHotel.dto.admins.AdminGridDTO(adm.id, acc.username, adm.jobTitle)
            FROM Admin adm
                JOIN adm.account acc
            """)
    Page<AdminGridDTO> getAdmins(Pageable pageable);


    @Query("""
            SELECT new com.BookingHotel.dto.admins.AdminUpdateDTO(acc.username, adm.jobTitle)
            FROM Admin adm
                JOIN adm.account acc
            WHERE acc.username = :username
            """)
    AdminUpdateDTO findAdminByUsername(@Param("username") String username);

    @Query("""
            SELECT adm
            FROM Admin adm
                JOIN adm.account acc
            WHERE acc.username = :username
            """)
    Optional<Admin> findOptionalAdminByUsername(@Param("username") String username);
}
