package com.BookingHotel.entity;

import javax.persistence.*;

@Entity
@Table(name = "Admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
    @Column(name="Id")
    private Long id;

    @Column(name="JobTitle")
    private String jobTitle;

    //one to one unidirectional/ komunikasi 1 arah
    @OneToOne(cascade = CascadeType.ALL) // relation
    @JoinColumn(name = "Username") // FK
    private Account account;

    public Admin() {
    }

    // to insert new admin
    public Admin(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Admin(Long id, String jobTitle, Account account) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", jobTitle='" + jobTitle + '\'' +
                ", account=" + account +
                '}';
    }
}
