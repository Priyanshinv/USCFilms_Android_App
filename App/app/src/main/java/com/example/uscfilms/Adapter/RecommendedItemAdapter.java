package com.example.uscfilms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.DetailsActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.RecommendedItemsModel;

import java.util.ArrayList;

public class RecommendedItemAdapter extends RecyclerView.Adapter<RecommendedItemAdapter.ViewHolder>{
    private ArrayList<RecommendedItemsModel> itemList;
    private Context context;
    public RecommendedItemAdapter(Context context, ArrayList<RecommendedItemsModel> itemList){
        this.context=context;
        this.itemList=itemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_items_layout,parent,false);
        return new RecommendedItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(itemList.get(position).getImage())
                .centerCrop()
                .into(holder.imageView);
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
        public ViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.recommendedImageCard);
        }
    }
}
