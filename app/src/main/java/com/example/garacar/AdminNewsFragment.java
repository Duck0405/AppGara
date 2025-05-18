package com.example.garacar;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.garacar.adapters.ArticleAdapter;
import com.example.garacar.models.Article;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminNewsFragment extends Fragment {

    private RecyclerView recyclerNews;
    private ArticleAdapter articleAdapter;
    private ArrayList<Article> articleList;
    private DatabaseReference databaseRef;

    private Button btnAddNews;

    public AdminNewsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_news_fragment, container, false);

        recyclerNews = view.findViewById(R.id.recyclerNews);
        recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));
        articleList = new ArrayList<>();
        articleAdapter = new ArticleAdapter(getContext(), articleList);
        recyclerNews.setAdapter(articleAdapter);

        databaseRef = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Articles");
        loadArticles();

        btnAddNews = view.findViewById(R.id.btnAddNews);

        btnAddNews.setOnClickListener(v -> {
            // Mở Activity hoặc Dialog để tạo bài viết mới
            Intent intent = new Intent(getActivity(), AddArticleActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void loadArticles() {
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                articleList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Article article = data.getValue(Article.class);
                    if (article != null) {
                        article.setKey(data.getKey());
                        articleList.add(article);
                    }
                }

                // Delay 1.5s rồi mới cập nhật adapter để tránh UI bị giật
                new Handler().postDelayed(() -> {
                    if (getActivity() != null && isAdded()) {
                        getActivity().runOnUiThread(() -> articleAdapter.notifyDataSetChanged());
                    }
                }, 1500);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
            }
        });
    }


}
