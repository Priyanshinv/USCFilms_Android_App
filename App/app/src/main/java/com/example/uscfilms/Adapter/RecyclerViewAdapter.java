package com.example.uscfilms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.DetailsActivity;
import com.example.uscfilms.ItemDetailsModel;
import com.example.uscfilms.R;
import com.example.uscfilms.SharedPreferencesManager;
import com.example.uscfilms.WatchListModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<ItemDetailsModel> itemList;
    SharedPreferencesManager sharedPreferencesManager;
    private Context context;
    public RecyclerViewAdapter(Context context, ArrayList<ItemDetailsModel> itemList){
        this.context=context;
        this.itemList=itemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        sharedPreferencesManager = new SharedPreferencesManager(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(itemList.get(position).getImage())
                .centerCrop()
                .into(holder.imageView);

       holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, holder.imageButton, Gravity.BOTTOM);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                if(sharedPreferencesManager.checkIfInSharedPreferences(itemList.get(position).getId())){
                    popup.getMenu().getItem(3).setTitle("Remove from Watchlist");
                }
                else{
                    popup.getMenu().getItem(3).setTitle("Add to Watchlist");
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Uri uri;
                        switch (item.getItemId()) {
                            case R.id.tmdb_link:
                                uri = Uri.parse(itemList.get(position).getTmdbLink());
                                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                                context.startActivity(intent);
                                break;
                            case R.id.fb_link:
                                uri = Uri.parse("https://www.facebook.com/sharer/sharer.php?u="+itemList.get(position).getTmdbLink());
                                intent= new Intent(Intent.ACTION_VIEW,uri);
                                context.startActivity(intent);
                                break;
                            case R.id.twitter_link:
                                uri = Uri.parse("https://twitter.com/intent/tweet?text=Check this out!&url="+itemList.get(position).getTmdbLink());
                                intent= new Intent(Intent.ACTION_VIEW,uri);
                                context.startActivity(intent);
                                break;
                            case R.id.watch_link:
                                Toast toast;
                                ItemDetailsModel itemDetailsModel = itemList.get(position);
                                WatchListModel watchListModel = new WatchListModel(itemDetailsModel.getImage(),itemDetailsModel.getMediaType(),itemDetailsModel.getId(), itemDetailsModel.getTitle());
                                if(sharedPreferencesManager.checkIfInSharedPreferences(itemList.get(position).getId())){
                                    sharedPreferencesManager.deleteFromSharedPreferences(watchListModel.getId());
                                    Toast.makeText(context,itemDetailsModel.getTitle()+" was removed from Watchlist", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    sharedPreferencesManager.addToSharedPreferences(watchListModel);
                                    Toast.makeText(context,itemDetailsModel.getTitle()+" was added to Watchlist", Toast.LENGTH_SHORT).show();

                                }
                                //View view = toast.getView();
                                //view.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

                                //TextView text = view.findViewById(android.R.id.message);
                                //text.setTextColor(Color.BLACK);

                                break;

                            default:
                                throw new IllegalStateException("Unexpected value: " + item.getItemId());
                        }

                        return true;
                    }

                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",itemList.get(position).getId());
                intent.putExtra("mediaType",itemList.get(position).getMediaType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageButton imageButton;
        public ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageCard);
            imageButton = itemView.findViewById(R.id.popup_button);

        }
    }
}
