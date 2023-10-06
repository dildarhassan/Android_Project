package com.example.minor_p.Model_Activities;

public class UserData_model {
    private String firstName;
    private String lastName;
    private String email;
    private String mobile_no;
    public String location;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserData_model(String firstName, String lastName, String email, String mobile_no, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile_no = mobile_no;
        this.location = location;

    }
}
