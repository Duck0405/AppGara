package com.example.garacar.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.garacar.R;
import com.example.garacar.models.Booking;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<Booking> bookingList;

    public BookingAdapter(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.tvService.setText("Dịch vụ: " + booking.service);
        holder.tvDateTime.setText("Thời gian: " + booking.date + " lúc " + booking.time);
        holder.tvCarInfo.setText("Xe: " + booking.carType + " - " + booking.plateNumber);
        holder.tvNote.setText("Ghi chú: " + (booking.note.isEmpty() ? "Không có" : booking.note));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvService, tvDateTime, tvCarInfo, tvNote;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvService = itemView.findViewById(R.id.tvService);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvCarInfo = itemView.findViewById(R.id.tvCarInfo);
            tvNote = itemView.findViewById(R.id.tvNote);
        }
    }
}
