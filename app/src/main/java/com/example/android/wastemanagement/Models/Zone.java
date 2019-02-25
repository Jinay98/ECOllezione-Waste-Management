package com.example.android.wastemanagement.Models;

public class Zone {
    String zoneCreator, zoneLat , zoneLong, category, city, cardinal;
    String date, time;
    Bandwidth bandwidth;

    public Zone() {
    }

    public Zone(String zoneCreator, String zoneLat, String zoneLong, String category, String city, String cardinal,
                String date, String time, Bandwidth bandwidth) {
        this.zoneCreator = zoneCreator;
        this.zoneLat = zoneLat;
        this.zoneLong = zoneLong;
        this.category = category;
        this.city = city;
        this.cardinal = cardinal;
        this.date = date;
        this.time = time;
        this.bandwidth = bandwidth;
    }

    public String getZoneCreator() {
        return zoneCreator;
    }

    public void setZoneCreator(String zoneCreator) {
        this.zoneCreator = zoneCreator;
    }

    public String getZoneLat() {
        return zoneLat;
    }

    public void setZoneLat(String zoneLat) {
        this.zoneLat = zoneLat;
    }

    public String getZoneLong() {
        return zoneLong;
    }

    public void setZoneLong(String zoneLong) {
        this.zoneLong = zoneLong;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCardinal() {
        return cardinal;
    }

    public void setCardinal(String cardinal) {
        this.cardinal = cardinal;
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

    public Bandwidth getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Bandwidth bandwidth) {
        this.bandwidth = bandwidth;
    }
}