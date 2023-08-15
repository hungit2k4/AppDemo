package com.example.appdemo.models;

public class Notify {

    private String id;
    private String contentNotify;
    private int countNotify;
    private String urlImage;

    public Notify() {
    }

    public Notify(String id, String contentNotify, int countNotify, String urlImage) {
        this.id = id;
        this.contentNotify = contentNotify;
        this.countNotify = countNotify;
        this.urlImage = urlImage;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentNotify() {
        return contentNotify;
    }

    public void setContentNotify(String contentNotify) {
        this.contentNotify = contentNotify;
    }

    public int getCountNotify() {
        return countNotify;
    }

    public void setCountNotify(int countNotify) {
        this.countNotify = countNotify;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
