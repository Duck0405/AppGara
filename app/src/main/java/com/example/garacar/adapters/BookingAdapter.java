package com.example.garacar.adapters;

import android.graphics.Color;
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
        holder.tvNote.setText("Ghi chú: " + (booking.note == null || booking.note.isEmpty() ? "Không có" : booking.note));

        // Status Admin: pending, confirmed, cancelled
        String status = booking.status;
        if (status == null) status = "";

        switch (booking.status.toLowerCase()) {
            case "pending":
                holder.tvStatus.setText("⏳ Chờ duyệt");
                holder.tvStatus.setBackgroundResource(R.drawable.bg_status_pending);
                break;
            case "confirmed":
                holder.tvStatus.setText("✅ Đã xác nhận");
                holder.tvStatus.setBackgroundResource(R.drawable.bg_status_confirmed);
                break;
            case "cancelled":
                holder.tvStatus.setText("❌ Đã huỷ");
                holder.tvStatus.setBackgroundResource(R.drawable.bg_status_cancelled);
                break;
            default:
                holder.tvStatus.setText("❓ Không rõ");
                holder.tvStatus.setBackgroundColor(Color.GRAY);
                break;
        }

    }


    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvService, tvDateTime, tvCarInfo, tvNote, tvStatus;;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvService = itemView.findViewById(R.id.tvService);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvCarInfo = itemView.findViewById(R.id.tvCarInfo);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
