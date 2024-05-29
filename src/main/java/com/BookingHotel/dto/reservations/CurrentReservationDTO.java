package com.BookingHotel.dto.reservations;

import com.BookingHotel.helper.Helper;

import java.time.LocalDate;

public class CurrentReservationDTO {
    private String roomNumber;

    private String roomType;

    private String customerName;

    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    //    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;

    private Double bill;

    private String reservationStatus;

    public CurrentReservationDTO() {
    }

    public CurrentReservationDTO(String roomNumber, String roomType, String customerName, LocalDate checkIn, LocalDate checkOut, Double bill, String reservationStatus) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.customerName = customerName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bill = bill;
        this.reservationStatus = reservationStatus;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getBillRupiah(){
        return Helper.formatRupiah(this.bill);
    }

    @Override
    public String toString() {
        return "CurrentReservationDTO{" +
                "roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", customerName='" + customerName + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", bill=" + bill +
                ", reservationStatus='" + reservationStatus + '\'' +
                '}';
    }
}
