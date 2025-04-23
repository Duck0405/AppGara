package com.example.garacar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;

public class NewsDetailActivity extends AppCompatActivity {

    TextView tvTitle, tvDate, tvContent;
    ImageView imgThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvContent = findViewById(R.id.tvContent);
        imgThumbnail = findViewById(R.id.imgThumbnail);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String content = intent.getStringExtra("content");
        String image = intent.getStringExtra("image");


        // Set dữ liệu lên giao diện
        tvTitle.setText(title);
        tvDate.setText(date);
        tvContent.setText(content);

        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.car)
                .into(imgThumbnail);

    }
}
