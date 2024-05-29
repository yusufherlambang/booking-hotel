package com.BookingHotel.entity;

import com.BookingHotel.helper.Helper;

import javax.persistence.*;

@Entity
@Table(name = "Rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
    @Column(name="Id")
    private Long id;

    @Column(name="RoomNumber")
    private String roomNumber;

    @Column(name="roomType")
    private String roomType;

    @Column(name="Price")
    private Double price;

    @Column(name="RoomStatus")
    private String roomStatus;

    public Room() {
    }

    //to insert new room
    public Room(String roomNumber, String roomType, Double price, String roomStatus) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.roomStatus = roomStatus;
    }

    public Room(Long id, String roomNumber, String roomType, Double price, String roomStatus) {
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

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", roomStatus='" + roomStatus + '\'' +
                '}';
    }
}
