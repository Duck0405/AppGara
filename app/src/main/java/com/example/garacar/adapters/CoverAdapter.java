package com.example.garacar.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.garacar.GaraDetailActivity;
import com.example.garacar.R;
import com.example.garacar.models.CoverModel;
import java.util.List;

public class CoverAdapter extends RecyclerView.Adapter<CoverAdapter.CoverViewHolder> {

    private Context context;
    private List<CoverModel> coverList;

    public CoverAdapter(Context context, List<CoverModel> coverList) {
        this.context = context;
        this.coverList = coverList;
    }

    @NonNull
    @Override
    public CoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cover_single, parent, false);
        return new CoverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoverViewHolder holder, int position) {
        CoverModel model = coverList.get(position);

        Glide.with(context).load(model.getImageUrl()).into(holder.coverImage);
        holder.coverNote.setText(model.getNote());

        holder.btnViewDetail.setOnClickListener(v -> {
            Intent intent = new Intent(context, GaraDetailActivity.class);
            intent.putExtra("image", model.getImageUrl());
            intent.putExtra("note", model.getNote());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return coverList.size();
    }

    public static class CoverViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImage;
        TextView coverNote;
        Button btnViewDetail;

        public CoverViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImage = itemView.findViewById(R.id.productImage_coverPage);
            coverNote = itemView.findViewById(R.id.productNoteCover);
            btnViewDetail = itemView.findViewById(R.id.productCheck_coverPage);
        }
    }
}



