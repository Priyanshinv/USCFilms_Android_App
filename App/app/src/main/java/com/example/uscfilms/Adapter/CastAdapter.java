package com.example.uscfilms.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uscfilms.CastData;
import com.example.uscfilms.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder>{
    List<CastData> list_data;
    Context ct;

    public CastAdapter(List<CastData> list_data, Context ct) {
        this.list_data = list_data;
        this.ct = ct;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CastData listData=list_data.get(position);
        holder.tvname.setText(listData.getName());
        Glide.with(ct)
                .load(listData.getImageurl())
                .into(holder.cImg);

    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView cImg;
        private TextView tvname;
        public ViewHolder(View itemView) {
            super(itemView);
            cImg=(CircleImageView)itemView.findViewById(R.id.crImageView);
            tvname=(TextView)itemView.findViewById(R.id.ac_name);
        }
    }
}
