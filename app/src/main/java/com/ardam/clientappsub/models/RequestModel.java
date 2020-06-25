package com.ardam.clientappsub.models;

public class RequestModel {
    String title;
    String imageLink;

    public RequestModel(String title, String imageLink) {
        this.title = title;
        this.imageLink = imageLink;
    }

    public RequestModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
