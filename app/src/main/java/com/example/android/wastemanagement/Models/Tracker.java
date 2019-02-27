package com.example.android.wastemanagement.Models;

public class Tracker {
    String donorName, donorImg, donorLat, donorLong, volunteerName, volunteerImg, volunteerLat, volunteerLong;
    String date, time, ngoAuthKey;
    long accept_status;
    Bandwidth toCollect;
    String industryItem;

    public Tracker(String donorName, String donorImg, String donorLat, String donorLong,
                   String volunteerName, String volunteerImg, String volunteerLat, String volunteerLong,
                   String date, String time, long accept_status, Bandwidth toCollect, String ngoAuthKey) {
        this.donorName = donorName;
        this.donorImg = donorImg;
        this.donorLat = donorLat;
        this.donorLong = donorLong;
        this.volunteerName = volunteerName;
        this.volunteerImg = volunteerImg;
        this.volunteerLat = volunteerLat;
        this.volunteerLong = volunteerLong;
        this.date = date;
        this.time = time;
        this.accept_status = accept_status;
        this.toCollect = toCollect;
        this.ngoAuthKey = ngoAuthKey;
    }

    public Tracker(String donorName, String donorImg, String donorLat, String donorLong, String volunteerName,
                   String volunteerImg, String volunteerLat, String volunteerLong,
                   String date, String time, long accept_status, String industryItem, String ngoAuthKey) {
        this.donorName = donorName;
        this.donorImg = donorImg;
        this.donorLat = donorLat;
        this.donorLong = donorLong;
        this.volunteerName = volunteerName;
        this.volunteerImg = volunteerImg;
        this.volunteerLat = volunteerLat;
        this.volunteerLong = volunteerLong;
        this.date = date;
        this.time = time;
        this.accept_status = accept_status;
        this.industryItem = industryItem;
        this.ngoAuthKey = ngoAuthKey;
    }

    public Tracker(){}

    public String getNgoAuthKey() {
        return ngoAuthKey;
    }

    public void setNgoAuthKey(String ngoAuthKey) {
        this.ngoAuthKey = ngoAuthKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorImg() {
        return donorImg;
    }

    public void setDonorImg(String donorImg) {
        this.donorImg = donorImg;
    }

    public String getDonorLat() {
        return donorLat;
    }

    public void setDonorLat(String donorLat) {
        this.donorLat = donorLat;
    }

    public String getDonorLong() {
        return donorLong;
    }

    public void setDonorLong(String donorLong) {
        this.donorLong = donorLong;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getVolunteerImg() {
        return volunteerImg;
    }

    public void setVolunteerImg(String volunteerImg) {
        this.volunteerImg = volunteerImg;
    }

    public String getVolunteerLat() {
        return volunteerLat;
    }

    public void setVolunteerLat(String volunteerLat) {
        this.volunteerLat = volunteerLat;
    }

    public String getVolunteerLong() {
        return volunteerLong;
    }

    public void setVolunteerLong(String volunteerLong) {
        this.volunteerLong = volunteerLong;
    }

    public long getAccept_status() {
        return accept_status;
    }

    public void setAccept_status(long accept_status) {
        this.accept_status = accept_status;
    }

    public Bandwidth getToCollect() {
        return toCollect;
    }

    public void setToCollect(Bandwidth toCollect) {
        this.toCollect = toCollect;
    }

    public String getIndustryItem() {
        return industryItem;
    }

    public void setIndustryItem(String industryItem) {
        this.industryItem = industryItem;
    }
}
