package com.example.garacar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

        initViews();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/");
        bookingRef = database.getReference("bookings");

        setupServiceDropdown();
        setupDateTimePickers();

        btnConfirm.setOnClickListener(v -> submitBooking());

        edtPhone.addTextChangedListener(new TextWatcher() {
            boolean isEditing = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isEditing) return;

                isEditing = true;

                // Nếu người dùng nhập chưa có số 0 ở đầu và độ dài > 0
                if (s.length() > 0 && !s.toString().startsWith("0")) {
                    s.insert(0, "0");
                }

                isEditing = false;
            }
        });

    }

    private void initViews() {
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        // Giới hạn nhập số điện thoại: chỉ cho nhập số và tối đa 11 ký tự

        edtPhone.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(11)
        });

        edtPhone.setKeyListener(DigitsKeyListener.getInstance("0123456789"));

        autoService = findViewById(R.id.autoService);
        edtDate = findViewById(R.id.edtDate);
        edtTime = findViewById(R.id.edtTime);
        edtCarType = findViewById(R.id.edtCarType);
        edtPlateNumber = findViewById(R.id.edtPlateNumber);
        edtNote = findViewById(R.id.edtNote);
        btnConfirm = findViewById(R.id.btnConfirm);

        edtPlateNumber.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting = false;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần làm gì ở đây
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần làm gì ở đây
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;

                isFormatting = true;

                String input = s.toString();

                // Nếu có dấu chấm, kiểm tra phần sau dấu chấm
                if (input.contains(".")) {
                    String[] parts = input.split("\\.");
                    if (parts.length > 1) {
                        // Nếu phần sau dấu chấm có nhiều hơn 2 chữ số, cắt bớt
                        if (parts[1].length() > 2) {
                            // Cắt bớt phần sau dấu chấm còn 2 ký tự
                            input = parts[0] + "." + parts[1].substring(0, 2);
                            edtPlateNumber.setText(input);
                            edtPlateNumber.setSelection(input.length()); // Đặt lại con trỏ vào cuối
                        }
                    }
                }

                // Format lại biển số theo kiểu chuẩn
                String formatted = formatLicensePlate(input);
                if (!formatted.equals(input)) {
                    edtPlateNumber.setText(formatted);
                    edtPlateNumber.setSelection(formatted.length());
                }

                isFormatting = false;
            }
        });
    }

    public static String formatLicensePlate(String raw) {
        // Loại bỏ mọi ký tự không phải chữ/số
        raw = raw.replaceAll("[^A-Za-z0-9]", "");

        if (raw.length() < 5) return raw; // Quá ngắn thì khỏi xử lý

        String province = raw.substring(0, Math.min(2, raw.length()));
        String type = raw.length() > 2 ? raw.substring(2, 3).toUpperCase() : "";

        // Lấy tối đa 5 số sau phần loại xe (type)
        int numberStart = 3;
        int numberEnd = Math.min(numberStart + 3, raw.length());
        String number = raw.substring(numberStart, numberEnd);

        // Nếu còn ký tự sau 5 số thì gán thành suffix
        String suffix = raw.length() > numberEnd ? raw.substring(numberEnd) : "";

        // Nếu có dấu chấm và có nhiều hơn 2 chữ số sau dấu chấm, chỉ lấy 2 số đầu
        if (suffix.contains(".")) {
            String[] parts = suffix.split("\\.");
            if (parts.length > 1) {
                // Cắt bỏ phần sau dấu chấm nếu quá 2 chữ số
                suffix = parts[0] + "." + parts[1].substring(0, Math.min(2, parts[1].length()));
            }
        }

        return province + type + "-" + number + (suffix.isEmpty() ? "" : "." + suffix);
    }




    private void setupServiceDropdown() {
        String selectedService = getIntent().getStringExtra("serviceName");
        if (selectedService != null) {
            autoService.setText(selectedService, false);
        }

        String[] services = {"Rửa xe", "Thay dầu", "Bọc ghế da", "Đánh bóng", "Vệ sinh nội thất"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, services);
        autoService.setAdapter(adapter);
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
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            // Kiểm tra và gán giá trị giờ bắt đầu trong khung giờ mở cửa
            int hour = 8; // Giờ bắt đầu là 8h sáng (mặc định)
            int minute = 0;

            if (currentHour >= 8 && currentHour < 12) {
                hour = currentHour; // Nếu giờ hiện tại trong khoảng 8h-12h
            } else if (currentHour >= 14 && currentHour < 18) {
                hour = currentHour; // Nếu giờ hiện tại trong khoảng 14h-18h
            }

            new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
                // Kiểm tra nếu giờ chọn trong khung giờ mở cửa
                if ((selectedHour >= 8 && selectedHour < 12) || (selectedHour >= 14 && selectedHour < 18)) {
                    String selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    edtTime.setText(selectedTime);
                } else {
                    Toast.makeText(this, "Giờ chọn phải trong khung giờ mở cửa (8h-12h, 14h-18h)", Toast.LENGTH_SHORT).show();
                }
            }, hour, minute, true).show();
        });
    }


    private boolean isFutureDate(String date, String time) {
        try {
            String[] d = date.split("/");
            String[] t = time.split(":");

            int day = Integer.parseInt(d[0]);
            int month = Integer.parseInt(d[1]) - 1;
            int year = Integer.parseInt(d[2]);

            int hour = Integer.parseInt(t[0]);
            int minute = Integer.parseInt(t[1]);

            Calendar selected = Calendar.getInstance();
            selected.set(year, month, day, hour, minute, 0);

            return selected.after(Calendar.getInstance());
        } catch (Exception e) {
            Log.e(TAG, "Lỗi parse date/time: " + e.getMessage());
            return false;
        }
    }

    private void submitBooking() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String service = autoService.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String time = edtTime.getText().toString().trim();
        String carType = edtCarType.getText().toString().trim();
        String plateNumber = edtPlateNumber.getText().toString().trim().toUpperCase(); // auto viết hoa
        String note = edtNote.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || service.isEmpty() || date.isEmpty() || time.isEmpty()
                || carType.isEmpty() || plateNumber.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!phone.matches("^0[0-9]{9,10}$")) {
            Toast.makeText(this, "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!plateNumber.matches("^[0-9]{2}[A-Z]-[0-9]{3}\\.[0-9]{2}$")) {
            Toast.makeText(this, "Biển số xe không đúng định dạng (VD: 30F-123.45)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isFutureDate(date, time)) {
            Toast.makeText(this, "Ngày giờ đặt lịch phải ở tương lai!", Toast.LENGTH_SHORT).show();
            return;
        }

        // 👉 BỎ CHECK SỐ ĐIỆN THOẠI VÀ BIỂN SỐ
        saveBooking(name, phone, service, date, time, carType, plateNumber, note);
    }


    private void saveBooking(String name, String phone, String service, String date, String time,
                             String carType, String plateNumber, String note) {
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
