package com.example.uscfilms;

public class ItemDetailsModel {
    String id;
    String tmdbLink;
    String image;
    String mediaType;
    String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTmdbLink() {
        return tmdbLink;
    }

    public void setTmdbLink(String tmdbLink) {
        this.tmdbLink = tmdbLink;
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

    public String getTitle() {
        return title;
    }

    public ItemDetailsModel(String id, String tmdbLink, String image, String mediaType, String title) {
        this.id = id;
        this.tmdbLink = tmdbLink;
        this.image = image;
        this.mediaType = mediaType;
        this.title=title;
    }
}
