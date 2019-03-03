package com.example.android.wastemanagement.Models;

public class DonationDriveData {

    String titleDDD, detailsDDD, commentDDD;
    long memberDD;
    String dateDDD, timeDDD;
    String id;

    public DonationDriveData() {
    }

    public DonationDriveData(String titleDDD, String detailsDDD, String commentDDD, long memberDD, String dateDDD, String timeDDD, String id) {
        this.titleDDD = titleDDD;
        this.detailsDDD = detailsDDD;
        this.commentDDD = commentDDD;
        this.memberDD = memberDD;
        this.dateDDD = dateDDD;
        this.timeDDD = timeDDD;
        this.id = id;
    }

    public String getTitleDDD() {
        return titleDDD;
    }

    public void setTitleDDD(String titleDDD) {
        this.titleDDD = titleDDD;
    }

    public String getDetailsDDD() {
        return detailsDDD;
    }

    public void setDetailsDDD(String detailsDDD) {
        this.detailsDDD = detailsDDD;
    }

    public String getCommentDDD() {
        return commentDDD;
    }

    public void setCommentDDD(String commentDDD) {
        this.commentDDD = commentDDD;
    }

    public long getMemberDD() {
        return memberDD;
    }

    public void setMemberDD(long memberDD) {
        this.memberDD = memberDD;
    }

    public String getDateDDD() {
        return dateDDD;
    }

    public void setDateDDD(String dateDDD) {
        this.dateDDD = dateDDD;
    }

    public String getTimeDDD() {
        return timeDDD;
    }

    public void setTimeDDD(String timeDDD) {
        this.timeDDD = timeDDD;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
