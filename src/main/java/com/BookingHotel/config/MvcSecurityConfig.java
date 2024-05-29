package com.BookingHotel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(1)
public class MvcSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**",
                        "/account/**",
                        "/home/**",
                        "/loginForm",
                        "/customer/registerForm",
                        "/registerForm",
                        "/customer/register").permitAll()
                .antMatchers("/customer/index",
                        "/customer",
                        "/room/index",
                        "/room",
                        "/room/upsertForm",
                        "/room/delete",
                        "/reservation/transaction/customer",
                        "/reservation/current-reservation",
                        "/customer/delete",
                        "/reservation/all-current-reservation",
                        "/reservation",
                        "/reservation/all-current-reservation/delete").hasAnyAuthority("Admin", "SuperAdmin")
                .antMatchers("/admin/index",
                        "/admin",
                        "/admin/delete").hasAuthority("SuperAdmin")
                .antMatchers("/admin/changeMyProfile").hasAuthority("Admin")
                .antMatchers("/customer/changeMyProfile",
                        "/room/vacant",
                        "/reservation/reserveForm",
                        "/reservation/reserve",
                        "/reservation/my-reservation",
                        "/reservation/my-transaction").hasAuthority("Customer")
                .anyRequest().authenticated() //..permitAll() //.authenticated()
                .and().formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/authenticating")
                .and().logout()
                .and().exceptionHandling().accessDeniedPage("/account/accessDenied");
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
}
