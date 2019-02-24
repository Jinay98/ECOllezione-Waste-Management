package com.example.android.wastemanagement.Models;

public class User {
    private String name;
    private String email;
    private long userContact;
    private String userImgUrl;
    private String userAddress;
    private String userCity;
    private String userCardinality;
    private long reg_status;
    private long userPoints;
    private String token;

    public User(String name, String email, long userContact, String userImgUrl,
                String userAddress, String userCity, String userCardinality, long reg_status, long userPoints, String token) {
        this.name = name;
        this.email = email;
        this.userContact = userContact;
        this.userImgUrl = userImgUrl;
        this.userAddress = userAddress;
        this.userCity = userCity;
        this.userCardinality = userCardinality;
        this.reg_status = reg_status;
        this.userPoints = userPoints;
        this.token = token;
    }

    public User(){}


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public long getUserContact() {
        return userContact;
    }

    public void setUserContact(long userContact) {
        this.userContact = userContact;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserCardinality() {
        return userCardinality;
    }

    public void setUserCardinality(String userCardinality) {
        this.userCardinality = userCardinality;
    }

    public long getReg_status() {
        return reg_status;
    }

    public void setReg_status(long reg_status) {
        this.reg_status = reg_status;
    }

    public long getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(long userPoints) {
        this.userPoints = userPoints;
    }
}

