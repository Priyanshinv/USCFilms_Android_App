package com.example.uscfilms;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uscfilms.Adapter.WatchListAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class WatchFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    View finalView;
    RecyclerView.Adapter recyclerView_Adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    ArrayList<WatchListModel> watchlist;
    ItemTouchHelper.Callback callback;
    public TextView textView;
   SharedPreferencesManager sharedPreferencesManager;
    ItemTouchHelper touchHelper;
    @Override
    public void onResume() {
        super.onResume();
        //recyclerView_Adapter.notifyDataSetChanged();
        touchHelper.attachToRecyclerView(null);
        populateWatchList();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watch,container,false);
        //finalView = view;
        textView = view.findViewById(R.id.watchAlert);
        textView.setVisibility(View.GONE);
        context=getContext();
        sharedPreferencesManager = new SharedPreferencesManager(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewWatch);
        recyclerViewLayoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        populateWatchList();
        return view;
    }

    private void populateWatchList() {
        Log.i("My activity","In populate");
        watchlist = new ArrayList<>();
        Set<String> watchListSet = sharedPreferencesManager.getSharedPreferencesList();
        Iterator<String> iterator = watchListSet.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            Gson gson = new Gson();
            WatchListModel watchListModel = gson.fromJson(element, WatchListModel.class);
            watchlist.add(watchListModel);
        }
        if(watchlist.size()==0)
            textView.setVisibility(View.VISIBLE);
        watchlist.sort((o1, o2)
                -> o1.getCounter().compareTo(
                o2.getCounter()));
        //Collections.reverse(watchlist);

        recyclerView_Adapter = new WatchListAdapter(this, context,watchlist);

        callback = new ItemMoveCallback((ItemMoveCallback.ItemTouchHelperContract) recyclerView_Adapter);
        touchHelper = new ItemTouchHelper(callback);
        //touchHelper.attachToRecyclerView(null);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(recyclerView_Adapter);
        recyclerView_Adapter.notifyDataSetChanged();

    }
}
