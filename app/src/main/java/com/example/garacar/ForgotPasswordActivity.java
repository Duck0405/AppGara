package com.example.garacar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailForgotPassword;
    private Button btnResetPassword;
    private TextView backToLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Khởi tạo Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Ánh xạ ID
        emailForgotPassword = findViewById(R.id.emailForgotPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        backToLogin = findViewById(R.id.backToLogin);

        // Xử lý khi nhấn nút "Gửi yêu cầu đặt lại mật khẩu"
        btnResetPassword.setOnClickListener(v -> resetPassword());

        // Quay lại màn hình đăng nhập
        backToLogin.setOnClickListener(v -> {
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void resetPassword() {
        String email = emailForgotPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gửi yêu cầu đặt lại mật khẩu qua Firebase
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ForgotPasswordActivity.this, "Vui lòng kiểm tra email để đặt lại mật khẩu!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(ForgotPasswordActivity.this, "Gửi yêu cầu thất bại. Hãy thử lại!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
