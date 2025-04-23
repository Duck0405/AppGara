package com.example.garacar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garacar.adapters.NewsAdapter;
import com.example.garacar.models.NewsItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerViewNews;
    private NewsAdapter newsAdapter;
    private List<NewsItem> newsList;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerViewNews = view.findViewById(R.id.recyclerViewNews);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Khởi tạo Firebase Realtime Database với URL cụ thể
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = database.getReference("Articles");

        // Tạo danh sách các bài viết mẫu
        newsList = new ArrayList<>();

        // Lấy dữ liệu từ Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Dọn sạch danh sách trước khi thêm dữ liệu mới
                newsList.clear();

                // Kiểm tra nếu dữ liệu tồn tại
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String title = snapshot.child("title").getValue(String.class);
                        String date = snapshot.child("date").getValue(String.class);
                        String content = snapshot.child("content").getValue(String.class);
                        String image = snapshot.child("image").getValue(String.class);



                        // Tạo đối tượng NewsItem và thêm vào danh sách
                        NewsItem newsItem = new NewsItem(title, date, content, image );
                        newsList.add(newsItem);
                    }

                    // Khởi tạo Adapter và gán vào RecyclerView
                    newsAdapter = new NewsAdapter(requireContext(), newsList);
                    recyclerViewNews.setAdapter(newsAdapter);
                } else {
                    Log.d("Firebase", "No data found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase", "Error loading data: " + databaseError.getMessage());
            }
        });

        return view;
    }
}
