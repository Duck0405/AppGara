package com.example.garacar;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garacar.adapters.BookingAdapter;
import com.example.garacar.adapters.BookingAdminAdapter;
import com.example.garacar.models.BookingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminBookingFragment extends Fragment {

    private RecyclerView recyclerViewBooking;
    private BookingAdminAdapter adapter;
    private ArrayList<BookingModel> bookingList;
    private DatabaseReference bookingRef;

    public AdminBookingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_booking_fragment, container, false);

        recyclerViewBooking = view.findViewById(R.id.recyclerViewBooking);
        recyclerViewBooking.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBooking.setHasFixedSize(true);

        bookingList = new ArrayList<>();
        adapter = new BookingAdminAdapter(getContext(), bookingList);
        recyclerViewBooking.setAdapter(adapter);

        bookingRef = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("bookings");

        loadBookings();

        return view;
    }

    private void loadBookings() {
        bookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookingList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    BookingModel model = data.getValue(BookingModel.class);
                    if (model != null) {
                        model.setBookingId(data.getKey());
                        bookingList.add(model);
                    }
                }

                if (getActivity() != null && isAdded()) {
                    getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi tải lịch đặt", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
