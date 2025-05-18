package com.example.garacar.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garacar.BookingDetailActivity;
import com.example.garacar.R;
import com.example.garacar.models.BookingModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookingAdminAdapter extends RecyclerView.Adapter<BookingAdminAdapter.BookingViewHolder> {

    private Context context;
    private List<BookingModel> bookingList;
    private DatabaseReference bookingRef;

    public BookingAdminAdapter(Context context, List<BookingModel> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
        this.bookingRef = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("bookings");
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_item, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingModel model = bookingList.get(position);

        holder.tvUserName.setText(model.getName());
        holder.tvServiceName.setText("Dịch vụ: " + model.getService());
        holder.tvDateTime.setText("Thời gian: " + model.getDate() + " - " + model.getTime());

        holder.itemView.setOnClickListener(v -> {
            BookingModel booking = bookingList.get(position);

            Intent intent = new Intent(context, BookingDetailActivity.class);
            intent.putExtra("name", booking.getName());
            intent.putExtra("service", booking.getService());
            intent.putExtra("date", booking.getDate());
            intent.putExtra("time", booking.getTime());
            intent.putExtra("carInfo", booking.getCarType());
            intent.putExtra("note", booking.getNote());
            intent.putExtra("status", booking.getStatus());

            context.startActivity(intent);
        });


        if (model.getNote() != null && !model.getNote().isEmpty()) {
            holder.tvNote.setText("Ghi chú: " + model.getNote());
            holder.tvNote.setVisibility(View.VISIBLE);
        } else {
            holder.tvNote.setVisibility(View.GONE);
        }

        // Hiển thị trạng thái với màu tương ứng
        switch (model.getStatus()) {
            case "pending":
                holder.tvStatus.setText("Chờ duyệt");
                holder.tvStatus.setTextColor(Color.parseColor("#F57C00"));
                break;
            case "confirmed":
                holder.tvStatus.setText("Đã xác nhận");
                holder.tvStatus.setTextColor(Color.parseColor("#388E3C"));
                break;
            case "cancelled":
                holder.tvStatus.setText("Đã huỷ");
                holder.tvStatus.setTextColor(Color.parseColor("#D32F2F"));
                break;
        }

        // Hiển thị nút duyệt/hủy chỉ khi pending
        if ("pending".equals(model.getStatus())) {
            holder.btnApprove.setVisibility(View.VISIBLE);
            holder.btnCancel.setVisibility(View.VISIBLE);
        } else {
            holder.btnApprove.setVisibility(View.GONE);
            holder.btnCancel.setVisibility(View.GONE);
        }

        holder.btnApprove.setOnClickListener(v -> {
            updateStatus(model.getBookingId(), "confirmed");
            model.setStatus("confirmed");          // update local model
            notifyItemChanged(position);           // update UI ngay
        });

        holder.btnCancel.setOnClickListener(v -> {
            updateStatus(model.getBookingId(), "cancelled");
            model.setStatus("cancelled");          // update local model
            notifyItemChanged(position);           // update UI ngay
        });

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvServiceName, tvDateTime, tvNote, tvStatus;
        Button btnApprove, btnCancel;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvNote = itemView.findViewById(R.id.tvNote);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }

    private void updateStatus(String bookingId, String newStatus) {
        bookingRef.child(bookingId).child("status").setValue(newStatus)
                .addOnSuccessListener(unused -> Toast.makeText(context, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Lỗi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
