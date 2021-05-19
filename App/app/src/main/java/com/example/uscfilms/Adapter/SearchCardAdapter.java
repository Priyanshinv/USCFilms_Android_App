package com.example.uscfilms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.DetailsActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.SearchCardModal;

import java.util.ArrayList;

public class SearchCardAdapter extends RecyclerView.Adapter<SearchCardAdapter.ViewHolder> {
    private ArrayList<SearchCardModal> searchModalArrayList;
    private Context context;
    public SearchCardAdapter(ArrayList<SearchCardModal> courseModalArrayList, Context context) {
        this.searchModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchCardModal modal = searchModalArrayList.get(position);
        String title = modal.getTitle();
        String[] words = title.split("\\s+");
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int start=0;
        for(String word: words){
            builder.append(word+" ")
                    .setSpan(new RelativeSizeSpan(1.3f), start, start+1, 0);
            start+=word.length()+1;
        }

        holder.title.setText(builder);
        String secondHalf = "";
        if(modal.getYear()!=null && modal.getYear()!=""){
            secondHalf = " ( "+modal.getYear()+" )";
        }
        holder.media_type.setText(modal.getMediaType()+secondHalf);
        holder.rating.setText(modal.getRating()+"");
        Glide.with(context)
                .asBitmap()
                .load(modal.getBackdropPath())
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",searchModalArrayList.get(position).getId());
                intent.putExtra("mediaType",searchModalArrayList.get(position).getMediaType());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView title;
        private ImageView image;
        private TextView media_type;
        private TextView rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our views with their ids.
            title = itemView.findViewById(R.id.titleSearchCard);
            image = itemView.findViewById(R.id.imageSearchCard);
            media_type = itemView.findViewById(R.id.media_type);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}
