package com.example.garacar.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.garacar.R;
import com.example.garacar.models.ServiceModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdminServiceAdapter extends RecyclerView.Adapter<AdminServiceAdapter.ViewHolder> {
    Context context;
    List<ServiceModel> serviceList;
    OnServiceDeletedListener listener;

    public AdminServiceAdapter(Context context, List<ServiceModel> serviceList, OnServiceDeletedListener listener) {
        this.context = context;
        this.serviceList = serviceList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceModel model = serviceList.get(position);

        holder.txtServiceName.setText(model.getServiceName());
        holder.txtServiceDesc.setText(model.getServiceDesc());
        holder.txtServicePrice.setText("Giá: " + model.getServicePrice() + "đ");
        Glide.with(context).load(model.getServiceImage()).into(holder.imgService);

        holder.btnToggleStatus.setText("Xoá");
        holder.btnToggleStatus.setEnabled(true);
        holder.btnToggleStatus.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;

            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc muốn xoá dịch vụ \"" + model.getServiceName() + "\"?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        Toast.makeText(context, "Đang xoá dịch vụ...", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(() -> {
                            FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                    .getReference("ServicesNew")
                                    .child(model.getId())
                                    .removeValue()
                                    .addOnSuccessListener(unused -> {
                                        if (currentPosition != RecyclerView.NO_POSITION && currentPosition < serviceList.size()) {
                                            serviceList.remove(currentPosition);
                                            notifyItemRemoved(currentPosition);
                                            notifyItemRangeChanged(currentPosition, serviceList.size());
                                        }
                                        Toast.makeText(context, "Xoá thành công!", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, "Xoá thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }, 1500);
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgService;
        TextView txtServiceName, txtServiceDesc, txtServicePrice;
        Button btnToggleStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgService = itemView.findViewById(R.id.imgService);
            txtServiceName = itemView.findViewById(R.id.txtServiceName);
            txtServiceDesc = itemView.findViewById(R.id.txtServiceDesc);
            txtServicePrice = itemView.findViewById(R.id.txtServicePrice);
            btnToggleStatus = itemView.findViewById(R.id.btnDeleteService);
        }
    }

    // Callback để load lại trang hoặc làm gì đó sau khi xoá xong
    public interface OnServiceDeletedListener {
        void onServiceDeleted();
    }
}
