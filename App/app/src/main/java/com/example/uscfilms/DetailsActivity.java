package com.example.uscfilms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.borjabravo.readmoretextview.ReadMoreTextView;
import com.bumptech.glide.Glide;
import com.example.uscfilms.Adapter.CastAdapter;
import com.example.uscfilms.Adapter.RecommendedItemAdapter;
import com.example.uscfilms.Adapter.RecyclerViewAdapter;
import com.example.uscfilms.Adapter.ReviewAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    String baseURL;
    ImageView backgroundImage;
    private RequestQueue requestQueue;
    private RecyclerView rv;
    private RecyclerView rrv;
    private List<CastData> list_data;
    private List<ReviewData> list_review;
    private ArrayList<RecommendedItemsModel> list_recommended;
    private CastAdapter adapter;
    private ReviewAdapter reviewAdapter;
    private GridLayoutManager gm;
    private LinearLayout spinner;
    private RelativeLayout relativeLayout;
    WatchListModel watchListModelFinal;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_details);
        backgroundImage = findViewById(R.id.background_image);
        backgroundImage.setVisibility(View.GONE);
        baseURL = getString(R.string.base_url);
        requestQueue = Volley.newRequestQueue(this);
        Intent myIntent = getIntent();
        String id = myIntent.getStringExtra("id");
        String mediaType = myIntent.getStringExtra("mediaType");
        Log.i("My activity", "Details act "+id+" "+mediaType);

        rv=(RecyclerView)findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        gm=gm =new GridLayoutManager(this,3);
        rv.setLayoutManager(gm);
        list_data=new ArrayList<>();
        list_review=new ArrayList<>();
        list_recommended=new ArrayList<>();
        adapter=new CastAdapter(list_data,this);
        relativeLayout = findViewById(R.id.relativeLayout);
        spinner=findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);

        getRecommendedItems(id, mediaType);
    }

    private void getRecommendedItems(String id, String mediaType) {
        String url;
        if (mediaType.equalsIgnoreCase("movie")) {
            url = baseURL + "/api/recommendedMovies1?params=" + id;
        } else{
            url = baseURL + "/api/recommendedTV1?params=" + id;
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.i("My activity","popular "+response.length());
                    for (int i = 0; i < Math.min(response.length(),10); i++) {
                        JSONArray jresponse = response.getJSONArray(i);
                        JSONObject jobj = jresponse.getJSONObject(0);
                        String image = jobj.getString("image");
                        String id = jobj.getInt("id")+"";
                        RecommendedItemsModel recommendedItemsModel = new RecommendedItemsModel(id,image,mediaType);
                        list_recommended.add(recommendedItemsModel);

                    }
                    RecyclerView recyclerView2 = findViewById(R.id.recommendedrecyclerView);
                    if(list_recommended.size()==0){
                        findViewById(R.id.recommendedHeading).setVisibility(View.GONE);
                        recyclerView2.setVisibility(View.GONE);
                    }
                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.HORIZONTAL,false);
                    recyclerView2.setLayoutManager(linearLayoutManager2);
                    RecommendedItemAdapter recommendedItemAdapter = new RecommendedItemAdapter(DetailsActivity.this,list_recommended);
                    recyclerView2.setAdapter(recommendedItemAdapter);
                    getCastData(id, mediaType);
                    getReviews(id, mediaType);
                    getItemVideo(id,mediaType);
                    getBackgroundImage(id,mediaType);
                    getItemDetails(id,mediaType);
                    spinner.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private void getReviews(String id, String mediaType) {
        String url;
        if (mediaType.equalsIgnoreCase("movie")) {
            url = baseURL + "/api/getMovieReviews?params=" + id;
        } else{
            url = baseURL + "/api/getTVReviews?params=" + id;
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0;i<Math.min(response.length(),3);i++) {
                        JSONObject jobj = response.getJSONObject(i);
                        String date="";
                        if(jobj.has("created_at"))
                            date = jobj.getString("created_at");
                        //Double rating = Double.parseDouble(jobj.getString("rating"));
                        //int scale = (int) Math.pow(10, 1);
                        //rating = rating*5/10;
                       // rating = (double) Math.round(rating * scale) / scale;
                        Integer rating = Integer.parseInt(jobj.getString("rating"));
                        rating = rating*5/10;
                        Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date);
                        String formattedDate = new SimpleDateFormat("E, MMM dd yyyy").format(date1);
                        Log.i("My activity","original date "+formattedDate);
                        ReviewData reviewData = new ReviewData(jobj.getString("author"),formattedDate,rating.toString(),jobj.getString("content"));
                        list_review.add(reviewData);
                    }
                    rrv=(RecyclerView) findViewById(R.id.reviewrecyclerview);
                    if(list_review.size()==0){
                        findViewById(R.id.reviewsHeading).setVisibility(View.GONE);
                        rrv.setVisibility(View.GONE);
                    }

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsActivity.this, LinearLayoutManager.VERTICAL,false);
                    rrv.setLayoutManager(linearLayoutManager);
                    reviewAdapter=new ReviewAdapter(list_review, DetailsActivity.this);
                    rrv.setAdapter(reviewAdapter);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private void getCastData(String id, String mediaType) {
        String url;
        if (mediaType.equalsIgnoreCase("movie")) {
            url = baseURL + "/api/getMovieCast?params=" + id;
        } else{
            url = baseURL + "/api/getTVCast?params=" + id;
        }
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=0;i<Math.min(response.length(),6);i++) {
                        JSONObject jobj = response.getJSONObject(i);
                        CastData castData = new CastData(jobj.getString("name"), jobj.getString("profile_path"));
                        list_data.add(castData);
                    }
                    if(list_data.size()==0){
                        findViewById(R.id.castHeading).setVisibility(View.GONE);
                        rv.setVisibility(View.GONE);
                    }
                    rv.setNestedScrollingEnabled(false);
                    rv.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }

    private void getItemDetails(String id, String mediaType) {
        String url;
        String tmdbUrl;
        if (mediaType.equalsIgnoreCase("movie")) {
            url = baseURL + "/api/getMovieDetails?params=" + id;
            tmdbUrl = "https://www.themoviedb.org/movie/";
        } else{
            url = baseURL + "/api/getTVDetails?params=" + id;
            tmdbUrl = "https://www.themoviedb.org/tv/";
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String title = response.getString("title");
                   TextView textView = findViewById(R.id.itemTitle);
                    textView.setText(title);
                   if(textView.getLineCount() > 1){
                       textView.setGravity(Gravity.CENTER_HORIZONTAL);
                   }
                   String overview = "";
                   if(response.has("overview")){
                       overview = response.getString("overview");
                   }
                   if(overview==null || overview.equals("") || overview.length()==0){
                       findViewById(R.id.overviewHeading).setVisibility(View.GONE);
                       findViewById(R.id.overview).setVisibility(View.GONE);

                   }
                   else{
                  ReadMoreTextView readMoreTextView = findViewById(R.id.overview);
                  readMoreTextView.setText(overview);
                   }
                   String genres = "";
                   if(response.has("genres")){
                       genres = response.getString("genres");
                   }
                   if(genres==null || genres.equals("") || genres.length()==0){
                       findViewById(R.id.genresHeading).setVisibility(View.GONE);
                       findViewById(R.id.genre).setVisibility(View.GONE);
                   }
                   else{
                  textView = findViewById(R.id.genre);
                  textView.setText(genres);
                   }
                    String year = "";
                    if(response.has("release_date")){
                        year = response.getString("release_date");
                    }
                    if(year==null || year.equals("") || year.length()==0){
                        findViewById(R.id.yearHeading).setVisibility(View.GONE);
                        findViewById(R.id.year).setVisibility(View.GONE);
                    }
                    else{
                        textView = findViewById(R.id.year);
                        textView.setText(year);
                    }

                    ImageButton imageButton = findViewById(R.id.fbButton);
                    imageButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            try {
                                Uri uri = Uri.parse("https://www.facebook.com/sharer/sharer.php?u="+tmdbUrl+response.getString("id"));
                                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    imageButton = findViewById(R.id.twitterButton);
                    imageButton.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            try {
                                Uri uri = Uri.parse("https://twitter.com/intent/tweet?text=Check this out!&url="+tmdbUrl+response.getString("id"));
                                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    watchListModelFinal = new WatchListModel(response.getString("poster_path"),response.getString("media_type"),response.getString("id"), response.getString("title"));

                    ImageButton imageButton1 = findViewById(R.id.watchButtonRemove);
                    ImageButton imageButton2 = findViewById(R.id.watchButtonAdd);
                    SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(DetailsActivity.this);
                    if(sharedPreferencesManager.checkIfInSharedPreferences(watchListModelFinal.getId()))
                        imageButton1.setVisibility(View.VISIBLE);
                    else
                        imageButton2.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }

    private void getBackgroundImage(String id, String mediaType) {
        String url;
        if (mediaType.equalsIgnoreCase("movie")) {
            url = baseURL + "/api/getMovieBackgroundImage?params=" + id;
        } else
            url = baseURL + "/api/getTVBackgroundImage?params=" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String image="";
                    if(response.has("image"));
                    image = response.getString("image");
                    if(image==null || image.equals("") || image.length()==0){
                        Glide.with(DetailsActivity.this)
                                .asBitmap()
                                .load(R.drawable.backdrop_path_placeholder)
                                .into(backgroundImage);
                    }
                    else {
                        Glide.with(DetailsActivity.this)
                                .asBitmap()
                                .load(image)
                                .into(backgroundImage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private void getItemVideo(String id, String mediaType) {
        String url;
        if (mediaType.equalsIgnoreCase("movie")) {
            url = baseURL + "/api/getMovieVideo?params=" + id;
        } else
            url = baseURL + "/api/getTVVideo?params=" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String youtubeLink = response.getString("key");
                    String type = response.getString("type");
                    if(type.equalsIgnoreCase("trailer") && !youtubeLink.equalsIgnoreCase("tzkWB85ULJY")){
                        CardView textView = findViewById(R.id.cardTitle);
                        //RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        //params.addRule(RelativeLayout.BELOW, R.id.youtube_player_view);
                        //textView.setLayoutParams(params);
                        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
                        getLifecycle().addObserver(youTubePlayerView);

                        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                String videoId = youtubeLink;
                                youTubePlayer.cueVideo(videoId, 0);
                            }
                        });
                    }
                    else{
                        findViewById(R.id.youtube_player_view).setVisibility(View.GONE);
                        backgroundImage.setVisibility(View.VISIBLE);
                        CardView textView = findViewById(R.id.cardTitle);
                        //RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        //params.addRule(RelativeLayout.BELOW, R.id.background_image);
                        //textView.setLayoutParams(params);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    public void onclickAdd(View view) {
        Log.i("My activity","Add clicked");
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(DetailsActivity.this);
        sharedPreferencesManager.addToSharedPreferences(watchListModelFinal);
        Toast toast = Toast.makeText(getApplicationContext(),watchListModelFinal.getTitle()+" was added to Watchlist", Toast.LENGTH_SHORT);
        toast.show();
        view.setVisibility(View.GONE);
        findViewById(R.id.watchButtonRemove).setVisibility(View.VISIBLE);

    }

    public void onClickRemove(View view) {
        Log.i("My activity","removed clicked");
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(DetailsActivity.this);
        sharedPreferencesManager.deleteFromSharedPreferences(watchListModelFinal.getId());
        Toast toast = Toast.makeText(getApplicationContext(),watchListModelFinal.getTitle()+" was removed from Watchlist", Toast.LENGTH_SHORT);
        toast.show();
        view.setVisibility(View.GONE);
        findViewById(R.id.watchButtonAdd).setVisibility(View.VISIBLE);
    }
}