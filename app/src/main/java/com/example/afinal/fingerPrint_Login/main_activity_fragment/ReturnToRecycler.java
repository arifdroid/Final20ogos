package com.example.afinal.fingerPrint_Login.main_activity_fragment;

import android.net.Uri;

class ReturnToRecycler {

    private String name;
    private String date;
    private String time;
    private String status;

    //18 june

    private Uri imageUri;


    public ReturnToRecycler(String name, String date, String time, String status, Uri imageUri) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
        this.imageUri = imageUri;
    }

    public ReturnToRecycler(String name, String date, String time, String status) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
