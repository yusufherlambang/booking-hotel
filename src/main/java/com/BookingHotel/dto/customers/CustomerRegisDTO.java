package com.BookingHotel.dto.customers;

import com.BookingHotel.validation.ComparePassword;
import com.BookingHotel.validation.UniqueEmail;
import com.BookingHotel.validation.UniqueUsername;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@UniqueEmail(message = "email already exists, please create another email", usernameField = "username", emailField = "email")
@ComparePassword(message = "confirm password must be equals with password", password = "password", passwordConfirmation = "passwordConfirmation")
public class CustomerRegisDTO {

    @UniqueUsername(message = "Username already exist, please create another username")
    @NotBlank(message="Username is required.")
    @Size(max=30, message="Username can't be no more than 30 characters.")
    private String username;

    @NotBlank(message="Password is required.")
    @Size(max=30, message="Password can't be no more than 30 characters.")
    private String password;

    @NotBlank(message="Confirm Password is required.")
    private String passwordConfirmation;

    private String name;

    @Email(message = "Format email is not valid")
    private String email;

    private String address;

    public CustomerRegisDTO() {
    }

    public CustomerRegisDTO(String username, String password, String passwordConfirmation, String name, String email, String address) {
        this.username = username;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
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
}
