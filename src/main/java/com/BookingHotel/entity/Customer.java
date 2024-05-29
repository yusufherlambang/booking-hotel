package com.BookingHotel.entity;

import javax.persistence.*;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
    @Column(name="Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name="Email")
    private String email;

    @Column(name="Address")
    private String address;

    //one to one unidirectional/ komunikasi 1 arah
    @OneToOne(cascade = CascadeType.ALL) // relation
    @JoinColumn(name = "Username") // FK
    private Account account;

    public Customer() {
    }

    // untuk insert customer & get by username
    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Customer(Long id, String name, String email, String address, Account account) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", account=" + account +
                '}';
    }
}
