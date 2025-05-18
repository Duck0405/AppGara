package com.example.garacar;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.garacar.models.BookingModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingDetailActivity extends AppCompatActivity {

    TextView tvUserName, tvPhone, tvService, tvCarType, tvPlate, tvDateTime, tvNote, tvStatus;
    DatabaseReference bookingRef;
    String bookingId;
    BookingModel booking;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        // Khai báo các view theo id
        tvUserName = findViewById(R.id.tvDetailUserName);
        tvPhone = findViewById(R.id.tv_phone);
        tvService = findViewById(R.id.tvDetailService);
        tvCarType = findViewById(R.id.tv_car_type);
        tvPlate = findViewById(R.id.tv_plate);
        tvDateTime = findViewById(R.id.tvDetailDateTime);
        tvNote = findViewById(R.id.tvDetailNote);
        tvStatus = findViewById(R.id.tvDetailStatus);

        bookingRef = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("bookings");

        // Lấy data truyền từ adapter qua intent
        booking = (BookingModel) getIntent().getSerializableExtra("bookings");

        if (booking != null) {
            bookingId = booking.getBookingId();
            updateUI(booking);
        } else {
            Toast.makeText(this, "Không có dữ liệu đặt lịch", Toast.LENGTH_SHORT).show();
            finish(); // Đóng Activity nếu không có dữ liệu
        }
    }

    private void updateUI(BookingModel booking) {
        tvUserName.setText(booking.getName());
        tvPhone.setText(booking.getPhone());
        tvService.setText(booking.getService());
        tvCarType.setText(booking.getCarType());
        tvPlate.setText(booking.getPlateNumber());
        tvDateTime.setText(booking.getDate() + " - " + booking.getTime());
        tvNote.setText(booking.getNote() == null || booking.getNote().isEmpty() ? "Không có" : booking.getNote());

        switch (booking.getStatus()) {
            case "pending":
                tvStatus.setText("Chờ duyệt");
                tvStatus.setTextColor(getResources().getColor(R.color.orange_700));
                break;
            case "confirmed":
                tvStatus.setText("Đã xác nhận");
                tvStatus.setTextColor(getResources().getColor(R.color.green_700));
                break;
            case "cancelled":
                tvStatus.setText("Đã huỷ");
                tvStatus.setTextColor(getResources().getColor(R.color.red_700));
                break;
            default:
                tvStatus.setText("Không xác định");
                tvStatus.setTextColor(getResources().getColor(R.color.grey_700));
                break;
        }
    }
}
