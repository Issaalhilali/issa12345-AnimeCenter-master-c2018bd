package com.example.animecenter.model;

public class GroImage {

    private String title;
    private String imageUrl;

    public GroImage(String imageUrl, String title) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public GroImage() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
