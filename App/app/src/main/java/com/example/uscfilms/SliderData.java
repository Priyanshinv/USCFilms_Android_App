package com.example.uscfilms;

public class SliderData {
    private String imgUrl;
    private String id;
    private String mediaType;

    public SliderData(String imgUrl, String id, String mediaType) {
        this.imgUrl = imgUrl;
        this.id = id;
        this.mediaType = mediaType;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
