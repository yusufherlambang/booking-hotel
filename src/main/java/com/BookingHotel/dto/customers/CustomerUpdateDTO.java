package com.BookingHotel.dto.customers;

import com.BookingHotel.validation.UniqueEmail;

import javax.validation.constraints.Email;

@UniqueEmail(message = "email already exists, please create another email", usernameField = "username", emailField = "email")
public class CustomerUpdateDTO {
    private Long id;

    private String name;

    @Email(message = "Format email is not valid")
    private String email;

    private String address;

    private String username;

    public CustomerUpdateDTO() {
    }

    public CustomerUpdateDTO(Long id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public CustomerUpdateDTO(Long id, String name, String email, String address, String username) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerUpdateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
