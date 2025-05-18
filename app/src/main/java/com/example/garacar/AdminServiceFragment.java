package com.example.garacar;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.garacar.adapters.AdminServiceAdapter;
import com.example.garacar.models.ServiceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnAddService;
    private List<ServiceModel> serviceList;
    private AdminServiceAdapter adapter;
    private DatabaseReference serviceRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_service_fragment, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewService);
        btnAddService = view.findViewById(R.id.btnAddService);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        serviceList = new ArrayList<>();
        adapter = new AdminServiceAdapter(getContext(), serviceList, () -> loadServices()); // callback khi xoá xong
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/");
        serviceRef = database.getReference("ServicesNew");

        loadServices();

        btnAddService.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddServiceActivity.class);
            startActivity(intent);
        });

        return view;
    }

    private void loadServices() {
        serviceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ServiceModel model = dataSnapshot.getValue(ServiceModel.class);
                    if (model != null) {
                        model.setId(dataSnapshot.getKey());
                        serviceList.add(model);
                    }
                }

                // Delay 1.5s rồi mới cập nhật adapter để tránh UI bị giật
                new Handler().postDelayed(() -> {
                    if (getActivity() != null && isAdded()) {
                        getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
                    }
                }, 1500);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
