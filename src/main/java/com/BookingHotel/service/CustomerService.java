package com.BookingHotel.service;

import com.BookingHotel.dto.customers.CustomerGridDTO;
import com.BookingHotel.dto.customers.CustomerRegisDTO;
import com.BookingHotel.dto.customers.CustomerUpdateDTO;
import com.BookingHotel.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Customer registerAccountCustomer(CustomerRegisDTO customerRegisDTO);

    Page<CustomerGridDTO> getAllCustomer(Pageable pageable, String name);

    CustomerUpdateDTO getCustomerByUsername(String username);

    void updateCustomerByUserName(CustomerUpdateDTO customerUpdateDTO, String username);

    boolean checkExistingEmail(String usernameValue, String emailValue);

    Customer findCustomer(String username);

    Customer getCustomerById(Long id);

    void deleteCustomerById(Long customerId);
}
