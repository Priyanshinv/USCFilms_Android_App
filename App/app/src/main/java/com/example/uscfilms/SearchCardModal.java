package com.example.uscfilms;

public class SearchCardModal {
    private String backdropPath;
    private String mediaType;
    private String year;
    private String title;
    private Double rating;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SearchCardModal(String backdropPath, String mediaType, String year, String title, Double rating, String id){
        this.backdropPath=backdropPath;
        this.mediaType=mediaType;
        this.year=year;
        this.title=title;
        this.rating=rating;
        this.id=id;
    }
    public void setBackdropPath(String backdropPath){
        this.backdropPath=backdropPath;
    }
    public String getBackdropPath(){
        return backdropPath;
    }
    public  void setMediaType(String mediaType){
        this.mediaType=mediaType;
    }
    public String getMediaType(){
        return mediaType;
    }
    public void setYear(String year){
        this.year=year;
    }
    public String getYear(){
        return year;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public  String getTitle(){
        return title;
    }
    public void setRating(Double rating){
        this.rating=rating;
    }
    public Double getRating(){
        return this.rating;
    }

}
