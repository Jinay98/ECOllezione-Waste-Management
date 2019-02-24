package com.example.android.wastemanagement.Models;

import java.util.HashMap;

public class Industry {
    private String name;
    private String industryEmail;
    private long industryContact;
    private String userImgUrl;
    private String industryAddress;
    private String industryCity;
    private String industryCardinality;
    private long reg_status;
    private String recycleType;
    private Volunteer toApprove;
    private HashMap<String, Boolean> approvedList;
    private String industryRegNumber;
    private String officalImgUrl;

    public Industry(String name, String industryEmail, long industryContact, String userImgUrl, String industryAddress,
                    String industryCity, String industryCardinality, long reg_status, String recycleType, Volunteer toApprove,
                    HashMap<String, Boolean> approvedList, String industryRegNumber, String officalImgUrl) {
        this.name = name;
        this.industryEmail = industryEmail;
        this.industryContact = industryContact;
        this.userImgUrl = userImgUrl;
        this.industryAddress = industryAddress;
        this.industryCity = industryCity;
        this.industryCardinality = industryCardinality;
        this.reg_status = reg_status;
        this.recycleType = recycleType;
        this.toApprove = toApprove;
        this.approvedList = approvedList;
        this.industryRegNumber = industryRegNumber;
        this.officalImgUrl = officalImgUrl;
    }

    public Industry(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustryEmail() {
        return industryEmail;
    }

    public void setIndustryEmail(String industryEmail) {
        this.industryEmail = industryEmail;
    }

    public long getIndustryContact() {
        return industryContact;
    }

    public void setIndustryContact(long industryContact) {
        this.industryContact = industryContact;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getIndustryAddress() {
        return industryAddress;
    }

    public void setIndustryAddress(String industryAddress) {
        this.industryAddress = industryAddress;
    }

    public String getIndustryCity() {
        return industryCity;
    }

    public void setIndustryCity(String industryCity) {
        this.industryCity = industryCity;
    }

    public String getIndustryCardinality() {
        return industryCardinality;
    }

    public void setIndustryCardinality(String industryCardinality) {
        this.industryCardinality = industryCardinality;
    }

    public long getReg_status() {
        return reg_status;
    }

    public void setReg_status(long reg_status) {
        this.reg_status = reg_status;
    }

    public String getRecycleType() {
        return recycleType;
    }

    public void setRecycleType(String recycleType) {
        this.recycleType = recycleType;
    }

    public Volunteer getToApprove() {
        return toApprove;
    }

    public void setToApprove(Volunteer toApprove) {
        this.toApprove = toApprove;
    }

    public HashMap<String, Boolean> getApprovedList() {
        return approvedList;
    }

    public void setApprovedList(HashMap<String, Boolean> approvedList) {
        this.approvedList = approvedList;
    }

    public String getIndustryRegNumber() {
        return industryRegNumber;
    }

    public void setIndustryRegNumber(String industryRegNumber) {
        this.industryRegNumber = industryRegNumber;
    }

    public String getOfficalImgUrl() {
        return officalImgUrl;
    }

    public void setOfficalImgUrl(String officalImgUrl) {
        this.officalImgUrl = officalImgUrl;
    }
}
