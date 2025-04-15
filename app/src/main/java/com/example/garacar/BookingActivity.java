package com.example.garacar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.*;

public class BookingActivity extends AppCompatActivity {

    private TextInputEditText edtName, edtPhone, edtCarType, edtPlateNumber, edtNote, edtDate, edtTime;
    private AutoCompleteTextView autoService;
    private MaterialButton btnConfirm;

    private DatabaseReference bookingRef;
    private static final String TAG = "BookingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Ánh xạ View
        initViews();

        // Firebase
        bookingRef = FirebaseDatabase.getInstance().getReference("bookings");

        // Danh sách dịch vụ
        setupServiceDropdown();

        // Date & Time Picker
        setupDateTimePickers();

        // Sự kiện nút xác nhận
        btnConfirm.setOnClickListener(v -> submitBooking());
    }

    private void initViews() {
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        autoService = findViewById(R.id.autoService);
        edtDate = findViewById(R.id.edtDate);
        edtTime = findViewById(R.id.edtTime);
        edtCarType = findViewById(R.id.edtCarType);
        edtPlateNumber = findViewById(R.id.edtPlateNumber);
        edtNote = findViewById(R.id.edtNote);
        btnConfirm = findViewById(R.id.btnConfirm);
    }

    private void setupServiceDropdown() {
        String[] services = {"Rửa xe", "Thay dầu", "Bọc ghế da", "Đánh bóng", "Vệ sinh nội thất"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, services);
        autoService.setAdapter(adapter);

        String selectedService = getIntent().getStringExtra("selectedService");
        if (selectedService != null) {
            autoService.setText(selectedService, false);
        }
    }

    private void setupDateTimePickers() {
        edtDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, day) -> {
                String selectedDate = String.format("%02d/%02d/%04d", day, month + 1, year);
                edtDate.setText(selectedDate);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        edtTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(this, (view, hour, minute) -> {
                String selectedTime = String.format("%02d:%02d", hour, minute);
                edtTime.setText(selectedTime);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        });
    }

    private void submitBooking() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String service = autoService.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String time = edtTime.getText().toString().trim();
        String carType = edtCarType.getText().toString().trim();
        String plateNumber = edtPlateNumber.getText().toString().trim();
        String note = edtNote.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || service.isEmpty() || date.isEmpty() || time.isEmpty()
                || carType.isEmpty() || plateNumber.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Dữ liệu đặt lịch
        Map<String, Object> booking = new HashMap<>();
        booking.put("name", name);
        booking.put("phone", phone);
        booking.put("service", service);
        booking.put("date", date);
        booking.put("time", time);
        booking.put("carType", carType);
        booking.put("plateNumber", plateNumber);
        booking.put("note", note);

        String bookingId = bookingRef.push().getKey();
        if (bookingId == null) {
            Toast.makeText(this, "Lỗi hệ thống: Không tạo được ID!", Toast.LENGTH_SHORT).show();
            return;
        }

        bookingRef.child(bookingId).setValue(booking)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Đặt lịch thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Firebase booking failed: ", e);
                    Toast.makeText(this, "Lỗi khi đặt lịch! " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}
