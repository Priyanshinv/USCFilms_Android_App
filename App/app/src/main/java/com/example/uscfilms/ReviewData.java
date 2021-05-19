package com.example.uscfilms;

public class ReviewData {
    String author;
    String createdAt;
    String rating;
    String content;

    public String getAuthor() {
        return author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRating() {
        return rating;
    }


    public String getContent() {
        return content;
    }

    public ReviewData(String author, String createdAt, String rating, String content) {
        this.author = author;
        this.createdAt = createdAt;
        this.rating = rating;
        this.content = content;
    }
}
