package com.example.android.wastemanagement.Models;

public class Post {
    String name;
    String userImgUrl;
    String message;
    String postImgUrl;
    String postCreatorAuthKey;

    public Post(String name, String userImgUrl, String message, String postImgUrl, String postCreatorAuthKey) {
        this.name = name;
        this.userImgUrl = userImgUrl;
        this.message = message;
        this.postImgUrl = postImgUrl;
        this.postCreatorAuthKey = postCreatorAuthKey;
    }

    public Post(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public String getPostCreatorAuthKey() {
        return postCreatorAuthKey;
    }

    public void setPostCreatorAuthKey(String postCreatorAuthKey) {
        this.postCreatorAuthKey = postCreatorAuthKey;
    }
}
