package com.example.android.wastemanagement.Models;

public class Society {
    String name, email; long acct1, acct2, acct3,userContact; String userAddress, sLat , sLong;

    public Society(String name, String email, long acct1, long acct2, long acct3, long userContact, String userAddress, String sLat, String sLong) {
        this.name = name;
        this.email = email;
        this.acct1 = acct1;
        this.acct2 = acct2;
        this.acct3 = acct3;
        this.userContact = userContact;
        this.userAddress = userAddress;
        this.sLat = sLat;
        this.sLong = sLong;
    }

    public Society() {
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

    public long getAcct1() {
        return acct1;
    }

    public void setAcct1(long acct1) {
        this.acct1 = acct1;
    }

    public long getAcct2() {
        return acct2;
    }

    public void setAcct2(long acct2) {
        this.acct2 = acct2;
    }

    public long getAcct3() {
        return acct3;
    }

    public void setAcct3(long acct3) {
        this.acct3 = acct3;
    }

    public long getUserContact() {
        return userContact;
    }

    public void setUserContact(long userContact) {
        this.userContact = userContact;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getsLat() {
        return sLat;
    }

    public void setsLat(String sLat) {
        this.sLat = sLat;
    }

    public String getsLong() {
        return sLong;
    }

    public void setsLong(String sLong) {
        this.sLong = sLong;
    }
}