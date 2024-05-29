package com.BookingHotel.service;

import com.BookingHotel.ApplicationUserDetail;
import com.BookingHotel.entity.Account;
import com.BookingHotel.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptionalById = accountRepository.findById(username);

        Account tempUser = null;

        if (accountOptionalById.isPresent()){
            tempUser = accountOptionalById.get();
        }

        return new ApplicationUserDetail(tempUser);
    }

    @Override
    public boolean checkExistingUsername(String username) {
        Long totalUsername = accountRepository.count(username);

        return totalUsername > 0 ? true : false;
    }
}
