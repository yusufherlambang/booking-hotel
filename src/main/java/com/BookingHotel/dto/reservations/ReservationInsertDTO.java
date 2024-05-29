package com.BookingHotel.dto.reservations;

import com.BookingHotel.helper.Helper;
import com.BookingHotel.validation.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Date(message ="You cannot check out before you checked in" ,dateChecked = "checkIn", dateCheckedAfter = "checkOut")
@Date(message ="You cannot check in in the past", dateChecked = "today", dateCheckedAfter = "checkIn")
@Date(message ="You cannot check out in the past", dateChecked = "today", dateCheckedAfter = "checkOut")
public class ReservationInsertDTO {

    private Long id;

    @NotNull(message = "Check in date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkIn;

    @NotNull(message = "Check out date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkOut;

    private String reservationStatus;

    private Long customerId;

    private Long roomId;

    private Double bill;

    private String roomNumber;

    private String roomType;

    private Double price;

    @JsonIgnore
    private LocalDate today = LocalDate.now();

    public ReservationInsertDTO() {
    }

    public ReservationInsertDTO(Long id, LocalDate checkIn, LocalDate checkOut, String reservationStatus, Long customerId, Long roomId, Double bill, String roomNumber, String roomType, Double price) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reservationStatus = reservationStatus;
        this.customerId = customerId;
        this.roomId = roomId;
        this.bill = bill;
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

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
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

    public String getPriceRupiah(){
        return Helper.formatRupiah(this.price);
    }

    public LocalDate getToday() {
        return today;
    }

    public Integer getTotalDay(){
        return Helper.countDay(this.checkIn, this.checkOut);
    }

    public Double getTotalPrice(){

        Double bill = getTotalDay() * this.price;

        return bill;
    }

    @Override
    public String toString() {
        return "ReservationInsertDTO{" +
                "id=" + id +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", reservationStatus='" + reservationStatus + '\'' +
                ", customerId=" + customerId +
                ", roomId=" + roomId +
                ", bill=" + bill +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                '}';
    }
}
