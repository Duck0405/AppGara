package com.example.garacar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garacar.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameEt, emailEt, passEt, cPassEt;
    private Button signUpBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        nameEt = findViewById(R.id.nameEt_signUpPage);
        emailEt = findViewById(R.id.emailEt_signUpPage);
        passEt = findViewById(R.id.PassEt_signUpPage);
        cPassEt = findViewById(R.id.cPassEt_signUpPage);
        signUpBtn = findViewById(R.id.signUpBtn_signUpPage);

        signUpBtn.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String name = nameEt.getText().toString().trim();
        String email = emailEt.getText().toString().trim();
        String password = passEt.getText().toString().trim();
        String confirmPassword = cPassEt.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đăng ký với Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fUser = mAuth.getCurrentUser();
                        if (fUser != null) {
                            String uid = fUser.getUid();

                            // Tạo đối tượng user và lưu vào DB
                            User user = new User(name, email, "", "user", "");

                            FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users")
                                    .child(uid)
                                    .setValue(user)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SignUpActivity.this, "Lỗi lưu thông tin: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
