package com.example.uscfilms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ReviewsActivity extends AppCompatActivity {
    TextView ratingView;
    TextView reviewByView;
    TextView reviewView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_reviews);
        Intent myIntent = getIntent();
        String author = myIntent.getStringExtra("author");
        String content = myIntent.getStringExtra("content");
        String createdAt = myIntent.getStringExtra("createdAt");
        String rating = myIntent.getStringExtra("rating");
        ratingView=findViewById(R.id.rating);
        reviewByView=findViewById(R.id.reviewBy);
        reviewView=findViewById(R.id.review);
        populateReviewDetails(author,content,createdAt,rating);
    }

    private void populateReviewDetails(String author, String content, String createdAt, String rating) {
        reviewByView.setText("by "+author+" on "+createdAt);
        ratingView.setText(rating+"/5");
        reviewView.setText(content);
    }
}