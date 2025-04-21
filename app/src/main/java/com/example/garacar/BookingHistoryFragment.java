package com.example.garacar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garacar.adapters.BookingAdapter;
import com.example.garacar.models.Booking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookingHistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private List<Booking> bookingList = new ArrayList<>();
    private DatabaseReference bookingRef;

    public BookingHistoryFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_booking_history_fragment, container, false);

        recyclerView = view.findViewById(R.id.bookingHistoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new BookingAdapter(bookingList);
        recyclerView.setAdapter(adapter);

        // Lấy userId từ Firebase Authentication
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Khởi tạo FirebaseDatabase và lấy tham chiếu đến bảng bookings
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/");
        bookingRef = database.getReference("bookings");

        // Gọi phương thức loadBookings và truyền currentUserId
        loadBookings(currentUserId);
        return view;
    }


    private void loadBookings(String currentUserId) {
        bookingRef.orderByChild("userId").equalTo(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Booking booking = child.getValue(Booking.class);
                    bookingList.add(booking);
                }
                Collections.reverse(bookingList); // Mới nhất lên đầu
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi tải lịch sử: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Duyệt lịch
    public void approveBooking(String bookingId) {
        bookingRef.child(bookingId).child("status").setValue("approved")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Lịch hẹn đã được duyệt", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi khi duyệt lịch: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Từ chối lịch
    public void rejectBooking(String bookingId) {
        bookingRef.child(bookingId).child("status").setValue("rejected")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Lịch hẹn đã bị từ chối", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi khi từ chối lịch: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
