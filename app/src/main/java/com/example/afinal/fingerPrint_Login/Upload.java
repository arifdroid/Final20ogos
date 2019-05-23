package com.example.afinal.fingerPrint_Login;

public class Upload {

    private String name;
    private String imageURL;

    public Upload(){

    }

    public Upload(String name, String imageURL){
        this.name = name;
        this.imageURL = imageURL;
    }

    public Upload(String imageURL){
        this.imageURL = imageURL;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
