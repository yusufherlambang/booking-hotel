package com.BookingHotel.repository;

import com.BookingHotel.dto.customers.CustomerGridDTO;
import com.BookingHotel.dto.customers.CustomerUpdateDTO;
import com.BookingHotel.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("""
            SELECT new com.BookingHotel.dto.customers.CustomerGridDTO(cus.id, cus.name, cus.email, cus.address)
            FROM Customer cus
            WHERE cus.name LIKE %:name%
            """)
    Page<CustomerGridDTO> findAll(Pageable pageable,
                                        @Param("name") String name);

    @Query("""
            SELECT new com.BookingHotel.dto.customers.CustomerUpdateDTO (cus.id, cus.name, cus.email, cus.address, acc.username)
            From Customer cus
                JOIN cus.account acc
            WHERE acc.username = :username
            """)
    CustomerUpdateDTO getByUsername(@Param("username") String username);

    @Query("""
            SELECT c
            FROM Customer c
                JOIN c.account u
            WHERE u.username = :username
            """)
    Optional<Customer> findCustByUsername(@Param("username") String username);



    @Query("""
			SELECT COUNT(cus.id)
			FROM Customer AS cus
			    JOIN cus.account acc
			WHERE cus.email = :emailValue AND acc.username != :usernameValue
			""")
    Long count(@Param("usernameValue") String usernameValue,
               @Param("emailValue") String emailValue);
}
