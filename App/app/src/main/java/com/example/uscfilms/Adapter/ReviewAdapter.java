package com.example.uscfilms.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uscfilms.CastData;
import com.example.uscfilms.DetailsActivity;
import com.example.uscfilms.R;
import com.example.uscfilms.ReviewData;
import com.example.uscfilms.ReviewsActivity;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    List<ReviewData> list_data;
    Context ct;
    public ReviewAdapter(List<ReviewData> list_data, Context ct) {
        this.list_data = list_data;
        this.ct = ct;
    }
    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list,parent,false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        ReviewData listData=list_data.get(position);
        String authorName = listData.getAuthor();
        if(authorName==null || authorName.equals("") || authorName.length()==0)
            authorName="Anonymous User";
        String firstHalf = "by "+authorName;
        String secondHalf = "";
        if(listData.getCreatedAt()!=null && !listData.getCreatedAt().equals("") && listData.getCreatedAt().length()!=0)
            secondHalf = " on "+listData.getCreatedAt();
        holder.reviewBy.setText(firstHalf+secondHalf);
        holder.rating.setText(listData.getRating()+"/5");
        holder.content.setText(listData.getContent());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, ReviewsActivity.class);
                intent.putExtra("author",list_data.get(position).getAuthor());
                intent.putExtra("createdAt",list_data.get(position).getCreatedAt());
                intent.putExtra("content",list_data.get(position).getContent());
                intent.putExtra("rating",list_data.get(position).getRating());
                ct.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView reviewBy;
        TextView rating;
        TextView content;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            reviewBy=(TextView)itemView.findViewById(R.id.reviewBy);
            rating=(TextView)itemView.findViewById(R.id.rating);
            content=(TextView)itemView.findViewById(R.id.review);
            cardView=itemView.findViewById(R.id.reviewcard);
        }
    }

}
