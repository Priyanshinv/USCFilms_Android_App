package com.example.uscfilms;

public class RecommendedItemsModel {
    String id;
    String image;
    String mediaType;

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getMediaType() {
        return mediaType;
    }

    public RecommendedItemsModel(String id, String image, String mediaType) {
        this.id = id;
        this.image = image;
        this.mediaType = mediaType;
    }
}
