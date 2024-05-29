package com.BookingHotel.validation;

import com.BookingHotel.service.CustomerService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidation implements ConstraintValidator<UniqueEmail, Object> {

    private String username;
    private String email;

    @Autowired
    private CustomerService customerService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        this.username = constraintAnnotation.usernameField();
        this.email = constraintAnnotation.emailField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        String usernameValue = (String) new BeanWrapperImpl(value).getPropertyValue(username);
        String emailValue = (String) new BeanWrapperImpl(value).getPropertyValue(email);

        return !customerService.checkExistingEmail(usernameValue, emailValue);
    }
}
