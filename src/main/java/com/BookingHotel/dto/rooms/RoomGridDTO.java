package com.BookingHotel.dto.rooms;

import com.BookingHotel.helper.Helper;

public class RoomGridDTO {

    private Long id;

    private String roomNumber;

    private String roomType;

    private Double price;

    private String roomStatus;

    public RoomGridDTO() {
    }

    public RoomGridDTO(Long id, String roomNumber, String roomType, Double price, String roomStatus) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.roomStatus = roomStatus;
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

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getPriceRupiah(){
        return Helper.formatRupiah(this.price);
    }
}
