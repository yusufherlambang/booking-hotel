package com.BookingHotel.dto.admins;

import com.BookingHotel.validation.ComparePassword;
import com.BookingHotel.validation.UniqueUsername;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ComparePassword(message = "confirm password must be equals with password", password = "password", passwordConfirmation = "passwordConfirmation")
public class AdminInsertDTO {

    @UniqueUsername(message = "Username already exist, please create another username")
    @NotBlank(message="Username is required.")
    @Size(max=30, message="Username can't be no more than 30 characters.")
    private String username;

    private String jobTitle;

    @NotBlank(message="Password is required.")
    @Size(max=30, message="Password can't be no more than 30 characters.")
    private String password;

    @NotBlank(message="Confirm Password is required.")
    private String passwordConfirmation;

    public AdminInsertDTO() {
    }

    public AdminInsertDTO(String username, String jobTitle, String password, String passwordConfirmation) {
        this.username = username;
        this.jobTitle = jobTitle;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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
}
