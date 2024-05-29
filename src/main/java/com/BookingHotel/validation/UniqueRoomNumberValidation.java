package com.BookingHotel.validation;

import com.BookingHotel.service.RoomService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueRoomNumberValidation implements ConstraintValidator<UniqueRoomNumber, Object> {

    private String id;
    private String roomNumber;

    @Autowired
    private RoomService roomService;

    @Override
    public void initialize(UniqueRoomNumber constraintAnnotation) {
        this.id = constraintAnnotation.idField();
        this.roomNumber = constraintAnnotation.roomNumberField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        Long idValue = (Long)(new BeanWrapperImpl(value).getPropertyValue(id));
        String roomValue = new BeanWrapperImpl(value).getPropertyValue(roomNumber).toString();
//        System.out.println(idValue);
        return !roomService.checkExistingRoomNumber(idValue, roomValue);
    }
}
