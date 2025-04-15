package com.example.garacar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GaraDetailActivity extends AppCompatActivity {
    Button contactGaraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gara_detail);

        contactGaraButton = findViewById(R.id.contactButton);

        LinearLayout mapLocationLayout = findViewById(R.id.mapLocationLayout);
        mapLocationLayout.setOnClickListener(v -> {
            String address = "117 Đường Nguyễn Viết Xuân, Phường Hưng Dũng, TP. Vinh";
            String mapUrl = "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(address);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
            startActivity(intent);
        });


        // Thêm sự kiện click cho nút "Liên hệ với Gara"
        contactGaraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi điện thoại đến số gara
                callGara();
            }
        });
    }


    // Phương thức gọi điện thoại
    private void callGara() {
        String phoneNumber = "tel:+84901234567";  // Số điện thoại của Gara (có thể thay đổi theo nhu cầu)

        // Tạo intent gọi điện
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));
        startActivity(intent);  // Mở ứng dụng gọi điện thoại

    }
}
