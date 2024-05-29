package com.BookingHotel.dto.rooms;

import com.BookingHotel.validation.UniqueRoomNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@UniqueRoomNumber(message = "Room Number already exist, please create another Room Number",
        idField = "id", roomNumberField = "roomNumber")
public class RoomUpdateDTO {

    private Long id;

    @NotBlank(message="Room Number is required.")
    @Size(max=3, message="Room Number can't be more than 3 characters.")
    private String roomNumber;

    @NotBlank(message="Room Type is required.")
    private String roomType;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive number & bigger than 0")
    private Double price;

    public RoomUpdateDTO() {
    }

    public RoomUpdateDTO(String roomNumber, String roomType, Double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
    }

    public RoomUpdateDTO(Long id, String roomNumber, String roomType, Double price) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
