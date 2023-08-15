package com.example.appdemo.models;

public class ChamCong {

    private String Manv;
    private int ngayLam;
    private int ngayNghi;
    private Double luong;

    public ChamCong() {
    }

    public ChamCong(String manv, int ngayLam, int ngayNghi, Double luong) {
        Manv = manv;
        this.ngayLam = ngayLam;
        this.ngayNghi = ngayNghi;
        this.luong = luong;
    }

    public String getManv() {
        return Manv;
    }

    public void setManv(String manv) {
        Manv = manv;
    }

    public int getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(int ngayLam) {
        this.ngayLam = ngayLam;
    }

    public int getNgayNghi() {
        return ngayNghi;
    }

    public void setNgayNghi(int ngayNghi) {
        this.ngayNghi = ngayNghi;
    }

    public Double getLuong() {
        return luong;
    }

    public void setLuong(Double luong) {
        this.luong = luong;
    }
}
