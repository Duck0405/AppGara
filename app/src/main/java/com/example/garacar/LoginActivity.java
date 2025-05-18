package com.example.garacar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEt, passEt;
    private Button loginBtn;
    private TextView signUpTv;
    private FirebaseAuth mAuth;

    private TextView forgottenPassTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Ánh xạ view
        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.PassEt);
        loginBtn = findViewById(R.id.loginBtn);
        signUpTv = findViewById(R.id.signUpTv);
        forgottenPassTv = findViewById(R.id.forgottenPassTv);

        // Xử lý sự kiện đăng nhập
        loginBtn.setOnClickListener(view -> loginUser());

        // Chuyển sang màn hình đăng ký nếu chưa có tài khoản
        signUpTv.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });

        // Xử lý sự kiện quên mật khẩu
        forgottenPassTv.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ email và mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                        if (email.equals("admin@gmail.com")) {
                            // Nếu là admin thì vào trang admin
                            startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
                        } else {
                            // Người dùng thường vào MainActivity
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
