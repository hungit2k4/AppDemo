package com.example.appdemo.data.acountdao;

public class ChucNang {
    private int icon;
    private String txt1;
    private String txt2;

    public ChucNang(int icon, String txt1, String txt2) {
        this.icon = icon;
        this.txt1 = txt1;
        this.txt2 = txt2;
    }

    public ChucNang() {
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }
}