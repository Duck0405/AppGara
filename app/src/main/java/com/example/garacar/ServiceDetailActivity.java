package com.example.garacar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class ServiceDetailActivity extends AppCompatActivity {

    private ImageView imgService;
    private TextView txtServiceName, txtServicePrice, txtServiceDesc;
    private MaterialButton btnBookNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        imgService = findViewById(R.id.imgService);
        txtServiceName = findViewById(R.id.txtServiceName);
        txtServicePrice = findViewById(R.id.txtServicePrice);
        txtServiceDesc = findViewById(R.id.txtServiceDesc);
        btnBookNow = findViewById(R.id.btnBookNow);

        // Lấy dữ liệu từ intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("serviceName");
        String image = intent.getStringExtra("serviceImage");
        String desc = intent.getStringExtra("serviceDesc");
        double price = intent.getDoubleExtra("price", 0.0);

        // Set dữ liệu vào UI
        txtServiceName.setText(name);
        txtServicePrice.setText(String.format("Giá: %,.0fđ", price));
        txtServiceDesc.setText(desc);
        Glide.with(this).load(image).into(imgService);

        // Xử lý nút đặt lịch
        btnBookNow.setOnClickListener(v -> {
            Intent bookIntent = new Intent(ServiceDetailActivity.this, BookingActivity.class);
            bookIntent.putExtra("serviceName", name); // truyền tên dịch vụ sang đặt lịch
            startActivity(bookIntent);
        });
    }
}
