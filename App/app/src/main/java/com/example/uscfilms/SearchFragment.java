package com.example.uscfilms;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.uscfilms.Adapter.RecyclerViewAdapter;
import com.example.uscfilms.Adapter.SearchCardAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
public class SearchFragment extends Fragment {
    private RecyclerView recView;
    TextView alertView;
    String baseURL;
    private SearchCardAdapter adapter;
    private RequestQueue requestQueue;
    private ArrayList<SearchCardModal> searchModalArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        recView = view.findViewById(R.id.recyclerViewSearch);
        alertView = view.findViewById(R.id.searchAlert);
        alertView.setVisibility(View.GONE);
        recView.setVisibility(View.VISIBLE);
        baseURL = getContext().getString(R.string.base_url);
        requestQueue = Volley.newRequestQueue(getContext());
        SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();
        Log.i("My activity","In search view");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("My activity","Search text: "+newText);
                if(newText==null || newText.equals("") || newText.length()==0){
                    recView.setVisibility(View.GONE);
                    alertView.setVisibility(View.GONE);
                    return true;
                }
                recView.setVisibility(View.GONE);
                buildRecyclerView(newText);
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                alertView.setVisibility(View.GONE);
                recView.setVisibility(View.GONE);
                return true;
            }
        });
        return view;
    }
    private void buildRecyclerView(String newText) {
        //searchModalArrayList = new ArrayList<>();
        if(newText==null || newText.equals("") || newText.length()==0){
            recView.setVisibility(View.GONE);
            return;
        }
        //recView.setVisibility(View.VISIBLE);
        String url = baseURL+"/api/searchResults?params="+newText;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    searchModalArrayList = new ArrayList<>();
                    Log.i("My activity","popular "+response.length());
                    if(response.length()==0){
                        alertView.setVisibility(View.VISIBLE);
                        recView.setVisibility(View.INVISIBLE);
                        return;
                    }
                    alertView.setVisibility(View.GONE);
                    recView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < Math.min(response.length(),20); i++) {
                        //JSONArray jresponse = response.getJSONArray(i);
                        JSONObject jobj = response.getJSONObject(i);
                        String image = jobj.getString("image");
                        String mediaType = jobj.getString("media_type");
                        String id = jobj.getString("id");
                        String title = jobj.getString("title");
                        String year="";
                        if(jobj.has("release_date"))
                            year = jobj.getString("release_date");
                        Double rating;
                        if(jobj.has("vote_average"))
                            rating = jobj.getDouble("vote_average");
                        else
                            rating = 0.0;
                        int scale = (int) Math.pow(10, 1);
                        rating = rating*5/10;
                        rating = (double) Math.round(rating * scale) / scale;
                        SearchCardModal searchCardModal = new SearchCardModal(image,mediaType.toUpperCase(),year,title.toUpperCase(),rating,id);
                        searchModalArrayList.add(searchCardModal);
                    }
                    adapter = new SearchCardAdapter(searchModalArrayList, getContext());
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    recView.setNestedScrollingEnabled(false);
                    recView.setLayoutManager(manager);
                    recView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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