package com.BookingHotel.repository;

import com.BookingHotel.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("""
            SELECT COUNT(*)
            From Account AS acc
            WHERE acc.username = :username
            """)
    Long count(@Param("username") String username);
}
