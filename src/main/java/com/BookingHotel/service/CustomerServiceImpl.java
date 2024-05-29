package com.BookingHotel.service;

import com.BookingHotel.config.MvcSecurityConfig;
import com.BookingHotel.dto.customers.CustomerGridDTO;
import com.BookingHotel.dto.customers.CustomerRegisDTO;
import com.BookingHotel.dto.customers.CustomerUpdateDTO;
import com.BookingHotel.entity.Account;
import com.BookingHotel.entity.Customer;
import com.BookingHotel.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer registerAccountCustomer(CustomerRegisDTO customerRegisDTO) {

        // Get password encoder
        // MvcSecurityConfig.passwordEncoder(); bisa langsung dipakai karena methode passwordEncoder sudah static
        // jadi tinggal panggil methodnya dari nama classnya, tidak perlu buat object baru
        PasswordEncoder passwordEncoder = MvcSecurityConfig.passwordEncoder();

        // hashing password / encode password
        String hashPassword = passwordEncoder.encode(customerRegisDTO.getPassword());

        Account accountCust = new Account(
                customerRegisDTO.getUsername(),
                hashPassword,
                "Customer"
        );

        Customer customer = new Customer(
                customerRegisDTO.getName(),
                customerRegisDTO.getEmail(),
                customerRegisDTO.getAddress()
        );

        customer.setAccount(accountCust);

        customerRepository.save(customer);

        System.out.println("ini customer  "+customer);
        return customer;
    }

    @Override
    public Page<CustomerGridDTO> getAllCustomer(Pageable pageable, String name) {

        return customerRepository.findAll(pageable, name);
    }

    @Override
    public CustomerUpdateDTO getCustomerByUsername(String username) {
        CustomerUpdateDTO customer = customerRepository.getByUsername(username);

        // agar bisa gunain anotasi unique email
        customer.setUsername(username);

        return customer;
    }

    @Override
    public void updateCustomerByUserName(CustomerUpdateDTO customerUpdateDTO, String username) {
        Optional<Customer> customerOptional = customerRepository.findCustByUsername(username);

        Customer customer = null;

        if (customerOptional.isPresent()){
            customer = customerOptional.get();
            customer.setName(customerUpdateDTO.getName());
            customer.setEmail(customerUpdateDTO.getEmail());
            customer.setAddress(customerUpdateDTO.getAddress());
        }

        customerRepository.save(customer);

    }

    @Override
    public boolean checkExistingEmail(String usernameValue, String emailValue) {

        long totalData = customerRepository.count(usernameValue, emailValue);

        return totalData > 0;
    }

    @Override
    public Customer findCustomer(String username) {
        Optional<Customer> customerOptional = customerRepository.findCustByUsername(username);

        Customer tempCust = null;

        if (customerOptional.isPresent()){
            tempCust = customerOptional.get();
        }

        return tempCust;
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);

        Customer custTemp = null;

        if (customerOptional.isPresent()){
            custTemp = customerOptional.get();
        }

        return custTemp;
    }

    @Override
    public void deleteCustomerById(Long customerId) {
        customerRepository.deleteById(customerId);
    }

}
