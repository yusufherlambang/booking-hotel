package com.BookingHotel.dto.reservations;

import com.BookingHotel.helper.Helper;

import java.time.LocalDate;

public class ReservationCustomerGridDTO {

    private Long id;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private String roomNumber;

    private Double bill;

    public ReservationCustomerGridDTO() {
    }

    public ReservationCustomerGridDTO(Long id, LocalDate checkIn, LocalDate checkOut, String roomNumber, Double bill) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomNumber = roomNumber;
        this.bill = bill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    public String getBillRupiah(){
        return Helper.formatRupiah(this.bill);
    }

    @Override
    public String toString() {
        return "ReservationCustomerGridDTO{" +
                "id=" + id +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", roomNumber='" + roomNumber + '\'' +
                ", bill=" + bill +
                '}';
    }
}
