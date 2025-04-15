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
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView newRecView, saleRecView, coverRecView;
    private ProductAdapter newAdapter, saleAdapter;
    private CoverAdapter coverAdapter;
    private List<Product> newProductList, saleProductList;
    private List<CoverModel> coverList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Ánh xạ RecyclerView
        coverRecView = view.findViewById(R.id.coverRecView);
        newRecView = view.findViewById(R.id.newRecView);
        saleRecView = view.findViewById(R.id.saleRecView);

        // Tạo danh sách dữ liệu giả lập
        newProductList = new ArrayList<>();
        saleProductList = new ArrayList<>();
        coverList = new ArrayList<>();

        // Thêm sản phẩm vào danh sách cover
        coverList.add(new CoverModel("https://cdn.elferspot.com/wp-content/uploads/2019/05/Porsche-911-991-GT3-for-sale--1024x683.jpg", "Porsche 911 Turbo"));
        coverList.add(new CoverModel("https://hips.hearstapps.com/hmg-prod/images/2024-mercedes-amg-gls63-103-642b777fa7be3.jpg?crop=0.734xw:0.825xh;0.128xw,0.0457xh&resize=768:*", "Audi RSQ8 Performance"));

        // Thêm sản phẩm mới
        newProductList.add(new Product("Rửa xe & chăm sóc ngoại thất", "https://www.mansory.com/sites/default/files/styles/fullwidth_image_with_custom_ratio/public/2020-11/mansory_audi_rsq8_02.jpg?itok=Chi8jiuP", 29.99, 4.5f, false, 0));
        newProductList.add(new Product("Chăm sóc nội thất", "https://www.mansory.com/sites/default/files/styles/1170_x_full_box_image/public/2022-11/MANSORY%20GHOST%2001.jpg?itok=NUYR1o_r", 120.00, 4.7f, false, 10));
        newProductList.add(new Product("Độ xe – nâng cấp tiện nghi", "https://www.mansory.com/sites/default/files/styles/fullwidth_image_with_custom_ratio/public/2020-11/mansory_audi_rsq8_02.jpg?itok=Chi8jiuP", 29.99, 4.5f, false, 0));
        newProductList.add(new Product("Bảo dưỡng định kỳ", "https://www.mansory.com/sites/default/files/styles/1170_x_full_box_image/public/2022-11/MANSORY%20GHOST%2001.jpg?itok=NUYR1o_r", 120.00, 4.7f, false, 10));


        // Thêm sản phẩm giảm giá
        saleProductList.add(new Product("Rửa xe & chăm sóc ngoại thất", "https://hips.hearstapps.com/hmg-prod/images/2022-range-rover-se-lwb-470-1665593876.jpg?crop=0.603xw:0.676xh;0.282xw,0.324xh&resize=768:*", 19.99, 4.0f, true, 15));
        saleProductList.add(new Product("Bảo dưỡng định kỳ", "https://hips.hearstapps.com/hmg-prod/images/2023-bmw-x7-xdrive-40i148-641c5b429bee5.jpg?crop=0.570xw:0.641xh;0.205xw,0.313xh&resize=768:*", 89.99, 4.8f, true, 20));

        // Khởi tạo Adapter
        coverAdapter = new CoverAdapter(getContext(), coverList);
        newAdapter = new ProductAdapter(getContext(), newProductList);
        saleAdapter = new ProductAdapter(getContext(), saleProductList);

        // Cấu hình RecyclerView
        coverRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        saleRecView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Gán Adapter vào RecyclerView
        coverRecView.setAdapter(coverAdapter);
        newRecView.setAdapter(newAdapter);
        saleRecView.setAdapter(saleAdapter);

        return view;
    }
}
