package com.example.garacar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class AddArticleActivity extends AppCompatActivity {

    private EditText edtTitle, edtContent, edtImageUrl;
    private Button btnPost;
    private DatabaseReference articlesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtImageUrl = findViewById(R.id.edtImageUrl);
        btnPost = findViewById(R.id.btnPost);

        articlesRef = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Articles");

        btnPost.setOnClickListener(view -> {
            String title = edtTitle.getText().toString().trim();
            String content = edtContent.getText().toString().trim();
            String imageUrl = edtImageUrl.getText().toString().trim();
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

            if (title.isEmpty() || content.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            String articleId = articlesRef.push().getKey();

            HashMap<String, Object> article = new HashMap<>();
            article.put("title", title);
            article.put("content", content);
            article.put("image", imageUrl);
            article.put("date", date);

            assert articleId != null;
            articlesRef.child(articleId).setValue(article).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Đăng bài thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Lỗi khi đăng bài", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
