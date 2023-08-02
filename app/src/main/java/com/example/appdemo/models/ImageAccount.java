package com.example.appdemo.models;

public class ImageAccount {
    private String nameImage;

    private String urlImage;
    private String url_Image_Background;

    public ImageAccount() {
    }

    public ImageAccount(String nameImage, String urlImage, String url_Image_Background) {
        this.nameImage = nameImage;

        this.urlImage = urlImage;
        this.url_Image_Background = url_Image_Background;
    }

    public String getUrl_Image_Background() {
        return url_Image_Background;
    }

    public void setUrl_Image_Background(String url_Image_Background) {
        this.url_Image_Background = url_Image_Background;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {

        this.urlImage = urlImage;
    }


}
