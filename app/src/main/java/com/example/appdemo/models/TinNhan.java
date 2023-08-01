package com.example.appdemo.models;

public class TinNhan {
    private String idGui,noiDung,idNhan;

    public TinNhan(String idGui, String noiDung, String idNhan) {
        this.idGui = idGui;
        this.noiDung = noiDung;
        this.idNhan = idNhan;
    }

    public TinNhan() {
    }

    public String getIdGui() {
        return idGui;
    }

    public void setIdGui(String idGui) {
        this.idGui = idGui;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getIdNhan() {
        return idNhan;
    }

    public void setIdNhan(String idNhan) {
        this.idNhan = idNhan;
    }
}
