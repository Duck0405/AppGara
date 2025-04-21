package com.example.garacar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail;
    private ImageView profileImage;
    private Button btnEditProfile, btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Ánh xạ View
        profileImage = view.findViewById(R.id.profile_image);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnLogout = view.findViewById(R.id.btnLogout);

        // Gán dữ liệu tạm (có thể lấy từ Firebase hoặc SharedPreferences)
        tvName.setText("Nguyễn Văn A");
        tvEmail.setText("vana@example.com");

        // Xử lý sự kiện
        btnEditProfile.setOnClickListener(v -> {
            // TODO: Chuyển sang activity hoặc dialog chỉnh sửa hồ sơ
        });

        btnLogout.setOnClickListener(v -> {
            // TODO: Đăng xuất, xóa session, quay lại Login
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish(); // tránh quay lại bằng nút back
        });

        return view;
    }
}
