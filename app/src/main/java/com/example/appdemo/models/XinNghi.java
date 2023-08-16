package com.example.appdemo.models;

public class XinNghi {
    String maNV,liDo,dateStart, dateEnd , status;

    public XinNghi() {
    }

    public XinNghi(String maNV, String liDo, String dateStart, String dateEnd, String status) {
        this.maNV = maNV;
        this.liDo = liDo;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.status = status;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getLiDo() {
        return liDo;
    }

    public void setLiDo(String liDo) {
        this.liDo = liDo;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
