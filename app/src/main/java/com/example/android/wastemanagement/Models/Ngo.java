package com.example.android.wastemanagement.Models;

import java.util.HashMap;

public class Ngo {
    private String name;
    private String ngoEmail;
    private long ngoContact;
    private String userImgUrl;
    private String ngoAddress;
    private String ngoCity;
    private String ngoCardinality;
    private long reg_status;
    private Bandwidth bandwidth;
    private Volunteer toApprove;
    private HashMap<String, Boolean> approvedList;
    private String ngoRegNumber;
    private String officalImgUrl;

    public Ngo(String name, String ngoEmail, long ngoContact, String userImgUrl, String ngoAddress, String ngoCity,
               String ngoCardinality, long reg_status, Bandwidth bandwidth, Volunteer toApprove,
               HashMap<String, Boolean> approvedList, String ngoRegNumber, String officalImgUrl) {
        this.name = name;
        this.ngoEmail = ngoEmail;
        this.ngoContact = ngoContact;
        this.userImgUrl = userImgUrl;
        this.ngoAddress = ngoAddress;
        this.ngoCity = ngoCity;
        this.ngoCardinality = ngoCardinality;
        this.reg_status = reg_status;
        this.bandwidth = bandwidth;
        this.toApprove = toApprove;
        this.approvedList = approvedList;
        this.ngoRegNumber = ngoRegNumber;
        this.officalImgUrl = officalImgUrl;
    }

    public Ngo(){}

    public Bandwidth getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Bandwidth bandwidth) {
        this.bandwidth = bandwidth;
    }

    public Volunteer getToApprove() {
        return toApprove;
    }

    public void setToApprove(Volunteer toApprove) {
        this.toApprove = toApprove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNgoEmail() {
        return ngoEmail;
    }

    public void setNgoEmail(String ngoEmail) {
        this.ngoEmail = ngoEmail;
    }

    public long getNgoContact() {
        return ngoContact;
    }

    public void setNgoContact(long ngoContact) {
        this.ngoContact = ngoContact;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getNgoAddress() {
        return ngoAddress;
    }

    public void setNgoAddress(String ngoAddress) {
        this.ngoAddress = ngoAddress;
    }

    public String getNgoCity() {
        return ngoCity;
    }

    public void setNgoCity(String ngoCity) {
        this.ngoCity = ngoCity;
    }

    public String getNgoCardinality() {
        return ngoCardinality;
    }

    public void setNgoCardinality(String ngoCardinality) {
        this.ngoCardinality = ngoCardinality;
    }

    public long getReg_status() {
        return reg_status;
    }

    public void setReg_status(long reg_status) {
        this.reg_status = reg_status;
    }

    public HashMap<String, Boolean> getApprovedList() {
        return approvedList;
    }

    public void setApprovedList(HashMap<String, Boolean> approvedList) {
        this.approvedList = approvedList;
    }

    public String getNgoRegNumber() {
        return ngoRegNumber;
    }

    public void setNgoRegNumber(String ngoRegNumber) {
        this.ngoRegNumber = ngoRegNumber;
    }

    public String getOfficalImgUrl() {
        return officalImgUrl;
    }

    public void setOfficalImgUrl(String officalImgUrl) {
        this.officalImgUrl = officalImgUrl;
    }
}
