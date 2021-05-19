package com.example.uscfilms;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.uscfilms.Adapter.RecyclerViewAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TVShowsFragment extends Fragment {
    String baseURL;
    private ArrayList<ItemDetailsModel> topRatedTVShows;
    private ArrayList<ItemDetailsModel> popularTVShows;
    ArrayList<SliderData> sliderDataArrayList;
    private RequestQueue requestQueue;
    private LinearLayout spinner;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;

    public TVShowsFragment(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tvshows,container,false);
        topRatedTVShows = new ArrayList<>();
        popularTVShows = new ArrayList<>();
        sliderDataArrayList = new ArrayList<>();
        TextView link = v.findViewById(R.id.footerLine1);
        relativeLayout = v.findViewById(R.id.relativeLayout);
        spinner=v.findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        link.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Uri uri = Uri.parse("https://www.themoviedb.org/");
                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                getContext().startActivity(intent);
                return true;
            }
        });
        baseURL = getContext().getString(R.string.base_url);
        requestQueue = Volley.newRequestQueue(getContext());
        populatePopularTVShows(v);
        return v;
    }

    private void populatePopularTVShows(View v) {
        String url = baseURL+"/api/popularTVShows1/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.i("My activity","popular "+response.length());
                    for (int i = 0; i < Math.min(response.length(),10); i++) {
                        JSONArray jresponse = response.getJSONArray(i);
                        JSONObject jobj = jresponse.getJSONObject(0);
                        String image = jobj.getString("image");
                        String tmdbLink = "https://www.themoviedb.org/tv/"+jobj.getInt("id");
                        String id = jobj.getInt("id")+"";
                        String title = jobj.getString("title");
                        ItemDetailsModel itemDetailsModel = new ItemDetailsModel(id,tmdbLink,image,"TV",title);
                        popularTVShows.add(itemDetailsModel);
                    }
                    LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
                    RecyclerView recyclerView2 = v.findViewById(R.id.recyclerView2);
                    recyclerView2.setLayoutManager(linearLayoutManager2);
                    RecyclerViewAdapter recyclerViewAdapter2 = new RecyclerViewAdapter(getContext(),popularTVShows);
                    recyclerView2.setAdapter(recyclerViewAdapter2);
                    populateCurrentTVShows(v);
                    populateTopRatedTVShows(v);
                    spinner.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);

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

    private void populateTopRatedTVShows(View v) {
        String url = baseURL+"/api/topRatedTVShows1/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.i("My activity","top "+response.length());
                    for (int i = 0; i < Math.min(response.length(),10); i++) {
                        JSONArray jresponse = response.getJSONArray(i);
                        JSONObject jobj = jresponse.getJSONObject(0);
                        String image = jobj.getString("image");
                        String tmdbLink = "https://www.themoviedb.org/tv/"+jobj.getInt("id");
                        String id = jobj.getInt("id")+"";
                        String title = jobj.getString("title");
                        ItemDetailsModel itemDetailsModel = new ItemDetailsModel(id,tmdbLink,image,"TV",title);
                        topRatedTVShows.add(itemDetailsModel);
                    }
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
                    RecyclerView recyclerView = v.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),topRatedTVShows);
                    recyclerView.setAdapter(recyclerViewAdapter);
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

    private void populateCurrentTVShows(View v) {
        String url = baseURL+"/api/trendingTVShows1/";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < Math.min(response.length(),6); i++) {
                        JSONArray jresponse = response.getJSONArray(i);
                        JSONObject jobj = jresponse.getJSONObject(0);
                        sliderDataArrayList.add(new SliderData(jobj.getString("image"), jobj.getString("id"), "TV"));
                    }
                    SliderView sliderView = v.findViewById(R.id.slider);
                    SliderAdapter adapter = new SliderAdapter(getContext(), sliderDataArrayList);
                    sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.setScrollTimeInSec(6);
                    sliderView.setAutoCycle(true);
                    sliderView.startAutoCycle();
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
}
