package com.example.garacar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garacar.adapters.CoverAdapter;
import com.example.garacar.adapters.ProductAdapter;
import com.example.garacar.models.CoverModel;
import com.example.garacar.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView coverRecView, newRecView, saleRecView;
    private CoverAdapter coverAdapter;
    private ProductAdapter newAdapter, saleAdapter;
    private List<CoverModel> coverList;
    private List<Product> newProductList, saleProductList;
    private DatabaseReference databaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ RecyclerView
        coverRecView = view.findViewById(R.id.coverRecView);
        newRecView = view.findViewById(R.id.newRecView);
        saleRecView = view.findViewById(R.id.saleRecView);

        // Khởi tạo list rỗng
        coverList = new ArrayList<>();
        newProductList = new ArrayList<>();
        saleProductList = new ArrayList<>();

        // Khởi tạo Adapter
        coverAdapter = new CoverAdapter(getContext(), coverList);
        newAdapter = new ProductAdapter(getContext(), newProductList);
        saleAdapter = new ProductAdapter(getContext(), saleProductList);

        // Cấu hình RecyclerView
        coverRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        saleRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        coverRecView.setAdapter(coverAdapter);
        newRecView.setAdapter(newAdapter);
        saleRecView.setAdapter(saleAdapter);

        // Kết nối Firebase
        databaseRef = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference();


        // Load dữ liệu từ Firebase
        loadCovers();
        loadNewServices();
        loadSaleServices();

        return view;
    }

    private void loadCovers() {
        databaseRef.child("Covers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                coverList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);
                    String note = dataSnapshot.child("note").getValue(String.class);
                    coverList.add(new CoverModel(imageUrl, note));
                }
                coverAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // TODO: Handle lỗi nếu cần
            }
        });
    }

    private void loadNewServices() {
        databaseRef.child("ServicesNew").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newProductList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("serviceName").getValue(String.class);
                    String image = dataSnapshot.child("serviceImage").getValue(String.class);
                    Double price = dataSnapshot.child("servicePrice").getValue(Double.class);

                    if (name != null && image != null && price != null) {
                        newProductList.add(new Product(name, image, price, 4.5f, false, 0));
                    }
                }
                newAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // TODO: Handle lỗi nếu cần
            }
        });
    }

    private void loadSaleServices() {
        databaseRef.child("ServicesSale").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                saleProductList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("serviceName").getValue(String.class);
                    String image = dataSnapshot.child("serviceImage").getValue(String.class);
                    Double price = dataSnapshot.child("servicePrice").getValue(Double.class);
                    Double discountPercent = dataSnapshot.child("discountPercentage").getValue(Double.class);

                    if (name != null && image != null && price != null && discountPercent != null) {
                        int discount = (int) (discountPercent * 100);
                        saleProductList.add(new Product(name, image, price, 4.2f, true, discount));
                    }
                }
                saleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // TODO: Handle lỗi nếu cần
            }
        });
    }
}
