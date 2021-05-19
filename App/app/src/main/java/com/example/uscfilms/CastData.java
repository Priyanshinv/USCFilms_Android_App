package com.example.uscfilms;

public class CastData {
    private String name;
    private String imageurl;

    public CastData(String name, String imageurl) {
        this.name = name;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public String getImageurl() {
        return imageurl;
    }
}
