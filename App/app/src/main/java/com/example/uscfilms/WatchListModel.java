package com.example.uscfilms;

public class WatchListModel {
    String image;
    String mediaType;
    String id;
    Long counter;
    String title;

    public String getId() {
        return id;
    }

    public String getTitle(){ return title; }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public WatchListModel(String image, String mediaType, String id, String title){
        this.image=image;
        this.mediaType=mediaType;
        this.id=id;
        this.title=title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
