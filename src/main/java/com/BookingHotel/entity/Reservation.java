package com.BookingHotel.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
    @Column(name="Id")
    private Long id;

    @Column(name="CheckIn")
    private LocalDate checkIn;

    @Column(name="CheckOut")
    private LocalDate checkOut;

    @Column(name="ReservationStatus")
    private String reservationStatus;

    @Column(name="CustomerId")
    private Long customerId;

    @ManyToOne
    @JoinColumn(name="CustomerId", insertable=false, updatable=false) // FK
    private Customer customer;

    @Column(name = "RoomId")
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = "RoomId", insertable=false, updatable=false) // FK
    private Room room;

    @Column(name = "Bill")
    private Double bill;

    public Reservation() {
    }

    public Reservation(LocalDate checkIn, LocalDate checkOut, String reservationStatus, Long customerId, Long roomId, Double bill) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reservationStatus = reservationStatus;
        this.customerId = customerId;
        this.roomId = roomId;
        this.bill = bill;
    }

    public Reservation(Long id, LocalDate checkIn, LocalDate checkOut, String reservationStatus, Long customerId, Long roomId, Double bill) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reservationStatus = reservationStatus;
        this.customerId = customerId;
        this.roomId = roomId;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Double getBill() {
        return bill;
    }

    public void setBill(Double bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", reservationStatus='" + reservationStatus + '\'' +
                ", customerId=" + customerId +
                ", customer=" + customer +
                ", roomId=" + roomId +
                ", room=" + room +
                ", bill=" + bill +
                '}';
    }
}
