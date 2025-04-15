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

public class CoverAdapter extends RecyclerView.Adapter<CoverAdapter.ViewHolder> {
    private Context context;
    private List<CoverModel> coverList;

    public CoverAdapter(Context context, List<CoverModel> coverList) {
        this.context = context;
        this.coverList = coverList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Thay đổi layout từ cover_item.xml -> cover_single.xml
        View view = LayoutInflater.from(context).inflate(R.layout.cover_single, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CoverModel model = coverList.get(position);
        Glide.with(context).load(model.getImageUrl()).into(holder.productImage);
        holder.productNote.setText(model.getNote());

        // Bắt sự kiện click vào nút "check"
        holder.checkButton.setOnClickListener(v -> {
            // Chuyển sang GaraDetailActivity
            Intent intent = new Intent(context, GaraDetailActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return coverList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productNote;
        Button checkButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage_coverPage);
            productNote = itemView.findViewById(R.id.productNoteCover);
            checkButton = itemView.findViewById(R.id.productCheck_coverPage);
        }
    }
}
