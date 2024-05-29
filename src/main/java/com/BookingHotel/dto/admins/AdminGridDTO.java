package com.BookingHotel.dto.admins;

public class AdminGridDTO {

    private Long id;

    private String username;

    private String jobTitle;

    public AdminGridDTO() {
    }

    public AdminGridDTO(Long id, String username, String jobTitle) {
        this.id = id;
        this.username = username;
        this.jobTitle = jobTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
}
