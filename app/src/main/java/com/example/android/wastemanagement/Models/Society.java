package com.example.android.wastemanagement.Models;

public class Society {
    String name, email;
    long accOrganic, accPlastic, accGlass,userContact,reg_status;
    String userAddress, sLat , sLong;
    String organiciKey, plasticiKey, glassiKey;

    public Society(String name, String email, long accOrganic, long accPlastic, long accGlass, long userContact,
                   long reg_status, String userAddress, String sLat, String sLong, String organiciKey, String plasticiKey,
                   String glassiKey) {
        this.name = name;
        this.email = email;
        this.accOrganic = accOrganic;
        this.accPlastic = accPlastic;
        this.accGlass = accGlass;
        this.userContact = userContact;
        this.reg_status = reg_status;
        this.userAddress = userAddress;
        this.sLat = sLat;
        this.sLong = sLong;
        this.organiciKey = organiciKey;
        this.plasticiKey = plasticiKey;
        this.glassiKey = glassiKey;
    }

    public Society(){}

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

    public long getAccOrganic() {
        return accOrganic;
    }

    public void setAccOrganic(long accOrganic) {
        this.accOrganic = accOrganic;
    }

    public long getAccPlastic() {
        return accPlastic;
    }

    public void setAccPlastic(long accPlastic) {
        this.accPlastic = accPlastic;
    }

    public long getAccGlass() {
        return accGlass;
    }

    public void setAccGlass(long accGlass) {
        this.accGlass = accGlass;
    }

    public long getUserContact() {
        return userContact;
    }

    public void setUserContact(long userContact) {
        this.userContact = userContact;
    }

    public long getReg_status() {
        return reg_status;
    }

    public void setReg_status(long reg_status) {
        this.reg_status = reg_status;
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

    public String getOrganiciKey() {
        return organiciKey;
    }

    public void setOrganiciKey(String organiciKey) {
        this.organiciKey = organiciKey;
    }

    public String getPlasticiKey() {
        return plasticiKey;
    }

    public void setPlasticiKey(String plasticiKey) {
        this.plasticiKey = plasticiKey;
    }

    public String getGlassiKey() {
        return glassiKey;
    }

    public void setGlassiKey(String glassiKey) {
        this.glassiKey = glassiKey;
    }
}