package com.example.appdemo.models;

public class Account {

    private int id;
    private String fullName;
    private String username;
    private String password;
    private String phoneNumber;


    public Account() {
    }

    public Account(int id, String username, String password, String url_avatar, String url_background, String fullName, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;

    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
