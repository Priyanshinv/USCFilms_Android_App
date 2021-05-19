package com.example.uscfilms.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.DetailsActivity;
import com.example.uscfilms.ItemMoveCallback;
import com.example.uscfilms.R;
import com.example.uscfilms.SearchCardModal;
import com.example.uscfilms.SharedPreferencesManager;
import com.example.uscfilms.WatchFragment;
import com.example.uscfilms.WatchListModel;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ViewHolder> implements ItemMoveCallback.ItemTouchHelperContract{
    ArrayList<WatchListModel> values;
    Context context;
    ViewGroup parent;
    WatchFragment watchFragment;
    int viewType;
    SharedPreferencesManager sharedPreferencesManager;

    public WatchListAdapter(WatchFragment watchFragment, Context context, ArrayList<WatchListModel> values){
        this.values = values;
        this.context = context;
        this.watchFragment=watchFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("My activity","On create "+parent);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchlist_layout,parent,false);
        this.parent=parent;
        this.viewType=viewType;
        sharedPreferencesManager = new SharedPreferencesManager(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // holder.alert.setVisibility(View.GONE);
        if(values.size()==0){
            //holder.alert.setVisibility(View.VISIBLE);
        }
        holder.getAdapterPosition();
        WatchListModel watchListModel = values.get(position);
        Log.i("Watch activity", "In onbind "+watchListModel.getId()+" "+position);
        Log.i("My Watch","In adapter onbind "+watchListModel+" "+watchListModel.getId()+" "+watchListModel.getCounter());
        holder.textView.setText(watchListModel.getMediaType());
        Glide.with(context)
                .asBitmap()
                .load(watchListModel.getImage())
                .centerCrop()
                .into(holder.imageView);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sharedPreferencesManager.deleteFromSharedPreferences(watchListModel.getId());
                values.remove(watchListModel);
                Toast toast = Toast.makeText(context,watchListModel.getTitle()+" was removed from Watchlist", Toast.LENGTH_SHORT);
                toast.show();
                if(values.size()==0){
                   watchFragment.textView.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",values.get(position).getId());
                intent.putExtra("mediaType",values.get(position).getMediaType());
               //((Activity) context).startActivityForResult(intent,1);
               context.startActivity(intent);
                //notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return values.size();
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        ArrayList<WatchListModel> list = new ArrayList<>();
        Long c1 = values.get(fromPosition).getCounter();
        Long c2 = values.get(toPosition).getCounter();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(values, i, i + 1);
            }
            for(int i= fromPosition;i<=toPosition;i++)
                list.add(values.get(i));
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(values, i, i - 1);
            }
            for(int i= toPosition;i<=fromPosition;i++)
                list.add(values.get(i));
        }

        Log.i("My watch","After swap "+values.get(fromPosition)+" "+values.get(toPosition));
        sharedPreferencesManager.swapPositions(list,Math.max(c1,c2),Math.min(c1,c2));
        notifyItemMoved(fromPosition, toPosition);
        //notifyItemChanged(fromPosition, toPosition);
        //notifyDataSetChanged();
    }

    @Override
    public void onRowSelected(WatchListAdapter.ViewHolder myViewHolder) {

    }

    @Override
    public void onRowClear(WatchListAdapter.ViewHolder myViewHolder) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;
        private ImageButton imageButton;
        public ViewHolder(@NonNull View v){
            super(v);
            textView = (TextView) v.findViewById(R.id.textview1);
            imageView = (ImageView) v.findViewById(R.id.imageWatchCard);
            imageButton = itemView.findViewById(R.id.removeItem);

        }

    }
}
