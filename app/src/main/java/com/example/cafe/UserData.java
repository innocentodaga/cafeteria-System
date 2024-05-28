package com.example.cafe;

public class UserData {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String userType;

    public UserData(String firstName, String lastName, String username, String email, String userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }
}
