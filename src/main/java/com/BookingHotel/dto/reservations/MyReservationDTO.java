package com.BookingHotel.dto.reservations;

import com.BookingHotel.helper.Helper;

import java.time.LocalDate;

public class MyReservationDTO {

    private Long id;

    private String roomNumber;

    private String roomType;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;

    private Double price;

    private Double bill;

    private String reservationStatus;

    public MyReservationDTO() {
    }

    public MyReservationDTO(Long id, String roomNumber, String roomType, LocalDate checkIn, LocalDate checkOut, Double price, Double bill, String reservationStatus) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.price = price;
        this.bill = bill;
        this.reservationStatus = reservationStatus;
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

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getPriceRupiah(){
        return Helper.formatRupiah(this.price);
    }

    public String getBillRupiah(){
        return Helper.formatRupiah(this.bill);
    }

    @Override
    public String toString() {
        return "MyReservationDTO{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", price=" + price +
                ", bill=" + bill +
                ", reservationStatus='" + reservationStatus + '\'' +
                '}';
    }
}
