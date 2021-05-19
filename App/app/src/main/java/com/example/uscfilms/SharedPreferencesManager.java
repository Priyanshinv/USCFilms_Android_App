package com.example.uscfilms;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SharedPreferencesManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        this.pref = context.getSharedPreferences("MyPref", 0);
        this.editor = this.pref.edit();
    }

    public void addToSharedPreferences(WatchListModel watchListModel){
        if(checkIfInSharedPreferences(watchListModel.getId()))
            return;
        watchListModel.setCounter(System.currentTimeMillis());
        //watchListModel.setCounter(System.currentTimeMillis());
        Log.i("My activity", "In add shared ");
        Set<String> watchListSet = pref.getStringSet("watchList1",new LinkedHashSet<>());
        Gson gson = new Gson();
        String json = gson.toJson(watchListModel);
        watchListSet.add(json);
        editor.clear();
        editor.putStringSet("watchList1",watchListSet);
        editor.commit();
        Log.i("My activity","Add List size "+watchListSet.size());
    }

    public void deleteFromSharedPreferences(String id){
        Set<String> watchListSet = pref.getStringSet("watchList1",new LinkedHashSet<>());
        Iterator<String> iterator = watchListSet.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            Gson gson = new Gson();
            WatchListModel watchListModel2 = gson.fromJson(element, WatchListModel.class);
            if(id.equals(watchListModel2.getId())){
                iterator.remove();
                break;
            }
        }
        editor.clear();
        editor.putStringSet("watchList1",watchListSet);
        editor.commit();
        Log.i("My activity","Delete List size "+watchListSet.size());
    }

    public boolean checkIfInSharedPreferences(String id){
        Set<String> watchListSet = pref.getStringSet("watchList1",new LinkedHashSet<>());
        Log.i("My activity","Check List size "+watchListSet.size());
        Iterator<String> iterator = watchListSet.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            Gson gson = new Gson();
            WatchListModel watchListModel = gson.fromJson(element, WatchListModel.class);
            if(watchListModel.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public Set<String> getSharedPreferencesList(){
        Log.i("My activity","Get List size "+pref.getStringSet("watchList1",new LinkedHashSet<>()));
        return pref.getStringSet("watchList1",new LinkedHashSet<>());
    }

    public void swapPositions(ArrayList<WatchListModel> list, Long c1, Long c2){
        Long counter = c2;
        Set<String> watchListSet = pref.getStringSet("watchList1",new LinkedHashSet<>());
        Log.i("My activity"," swap "+c1+" "+c2);
        for(WatchListModel watchListModel:list){
            Log.i("My activity","Yup "+watchListModel.getId());
            Iterator<String> iterator = watchListSet.iterator();
            while (iterator.hasNext()) {
                String element = iterator.next();
                Gson gson = new Gson();
                WatchListModel watchListModel2 = gson.fromJson(element, WatchListModel.class);
                if(watchListModel2.getId().equals(watchListModel.getId())){
                    iterator.remove();
                    watchListModel.setCounter(counter++);
                    gson = new Gson();
                    String json = gson.toJson(watchListModel);
                    watchListSet.add(json);
                    break;
                }

            }

        }
        for(String s: watchListSet){
            Gson gson = new Gson();
            WatchListModel watchListModel2 = gson.fromJson(s, WatchListModel.class);
            Log.i("My activity","In watchlist set loop "+watchListModel2.getId()+" "+watchListModel2.getCounter());
        }
        editor.clear();
        editor.putStringSet("watchList1",watchListSet);
        editor.commit();

    }

}
