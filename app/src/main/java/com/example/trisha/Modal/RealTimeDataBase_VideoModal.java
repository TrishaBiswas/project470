package com.example.trisha.Modal;

public class RealTimeDataBase_VideoModal {

    String title, vurl;

    public RealTimeDataBase_VideoModal() {
    }

    public RealTimeDataBase_VideoModal(String title, String vurl) {
        this.title = title;
        this.vurl = vurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }
}
