package com.example.garacar.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.garacar.R;
import com.example.garacar.models.Article;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context context;
    private List<Article> articles;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);

        holder.txtTitle.setText(article.getTitle());
        holder.txtDate.setText(article.getDate());
        Glide.with(context).load(article.getImage()).into(holder.imgArticle);

        holder.btnDelete.setText("Xoá");
        holder.btnDelete.setEnabled(true);
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc muốn xoá bài viết \"" + article.getTitle() + "\"?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        // Show loading nếu muốn (nếu có)

                        FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                .getReference("Articles")
                                .child(article.getKey())
                                .removeValue()
                                .addOnSuccessListener(unused -> {
                                    // Thêm delay 500ms trước khi update UI
                                    new android.os.Handler().postDelayed(() -> {
                                        int pos = holder.getAdapterPosition();
                                        if (pos != RecyclerView.NO_POSITION) {
                                            articles.remove(pos);
                                            notifyItemRemoved(pos);
                                            notifyItemRangeChanged(pos, articles.size());
                                        }
                                        Toast.makeText(context, "Xoá bài viết thành công", Toast.LENGTH_SHORT).show();
                                    }, 500); // 0.5 giây delay
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(context, "Xoá thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDate;
        ImageView imgArticle;
        Button btnDelete;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            imgArticle = itemView.findViewById(R.id.imgArticle);
            btnDelete = itemView.findViewById(R.id.btnDeleteArticle);
        }
    }
}
