package com.example.android.wastemanagement.Models;

public class Volunteer {
    private String name;
    private String volunteerEmail;
    private long volunteerContact;
    private String userImgUrl;
    private String volunteerAddress;
    private long reg_status;
    private long accept_status;
    private String ngoAuthkey;
    private String govnImgUrl;

    public Volunteer(String name, String volunteerEmail, long volunteerContact, String userImgUrl,
                     String volunteerAddress, long reg_status, long accept_status, String ngoAuthkey, String govnImgUrl) {
        this.name = name;
        this.volunteerEmail = volunteerEmail;
        this.volunteerContact = volunteerContact;
        this.userImgUrl = userImgUrl;
        this.volunteerAddress = volunteerAddress;
        this.reg_status = reg_status;
        this.accept_status = accept_status;
        this.ngoAuthkey = ngoAuthkey;
        this.govnImgUrl = govnImgUrl;
    }

    public Volunteer(){}

    public String getNgoAuthkey() {
        return ngoAuthkey;
    }

    public void setNgoAuthkey(String ngoAuthkey) {
        this.ngoAuthkey = ngoAuthkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVolunteerEmail() {
        return volunteerEmail;
    }

    public void setVolunteerEmail(String volunteerEmail) {
        this.volunteerEmail = volunteerEmail;
    }

    public long getVolunteerContact() {
        return volunteerContact;
    }

    public void setVolunteerContact(long volunteerContact) {
        this.volunteerContact = volunteerContact;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getVolunteerAddress() {
        return volunteerAddress;
    }

    public void setVolunteerAddress(String volunteerAddress) {
        this.volunteerAddress = volunteerAddress;
    }

    public long getReg_status() {
        return reg_status;
    }

    public void setReg_status(long reg_status) {
        this.reg_status = reg_status;
    }

    public long getAccept_status() {
        return accept_status;
    }

    public void setAccept_status(long accept_status) {
        this.accept_status = accept_status;
    }

    public String getGovnImgUrl() {
        return govnImgUrl;
    }

    public void setGovnImgUrl(String govnImgUrl) {
        this.govnImgUrl = govnImgUrl;
    }
}
