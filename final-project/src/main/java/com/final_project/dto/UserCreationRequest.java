package com.final_project.dto;

import jakarta.validation.constraints.Size;

public class UserCreationRequest {
    @Size(min=1, message = "Username cannot be empty")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Size(min=1, message = "Email cannot be empty")
    private String email;

    @Size(min=1, message = "First name cannot be empty")
    private String firstName;

    @Size(min=1, message = "Last name cannot be empty")
    private String lastName;
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
