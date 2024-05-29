package com.BookingHotel.dto.reservations;

import com.BookingHotel.helper.Helper;

import java.time.LocalDate;

public class AllCurrentReservationDTO {

    private Long id;

    private String customerName;

    private String roomNumber;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Double bill;

    private String reservationStatus;

    public AllCurrentReservationDTO() {
    }

    public AllCurrentReservationDTO(Long id, String customerName, String roomNumber, LocalDate checkIn, LocalDate checkOut, Double bill, String reservationStatus) {
        this.id = id;
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bill = bill;
        this.reservationStatus = reservationStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
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

    public Integer getTotalDays(){
        return Helper.countDay(this.checkIn, this.checkOut);
    }

    public String getBillRupiah(){
        return Helper.formatRupiah(this.bill);
    }

    @Override
    public String toString() {
        return "AllCurrentReservationDTO{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", bill=" + bill +
                ", reservationStatus='" + reservationStatus + '\'' +
                '}';
    }
}
