package com.example.garacar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.garacar.NewsDetailActivity;
import com.example.garacar.R;
import com.example.garacar.models.NewsItem;

import java.util.List;
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<NewsItem> newsList;

    public NewsAdapter(Context context, List<NewsItem> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = newsList.get(position);
        holder.tvTitle.setText(newsItem.getTitle());
        holder.tvDate.setText(newsItem.getDate());

        Glide.with(context)
                .load(newsItem.getImage())
                .placeholder(R.drawable.car)
                .into(holder.imgThumbnail);

        // Xử lý khi click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("title", newsItem.getTitle());
            intent.putExtra("date", newsItem.getDate());
            intent.putExtra("content", newsItem.getContent());
            intent.putExtra("image", newsItem.getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;
        ImageView imgThumbnail;

        public NewsViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDate = itemView.findViewById(R.id.tvDate);
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail);
        }
    }
}
