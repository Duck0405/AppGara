package com.example.garacar;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.garacar.models.ServiceModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddServiceActivity extends AppCompatActivity {

    private EditText edtServiceName, edtServiceDesc, edtServicePrice, edtServiceImage;
    private Button btnAddService;
    private DatabaseReference serviceRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        edtServiceName = findViewById(R.id.edtServiceName);
        edtServiceDesc = findViewById(R.id.edtServiceDesc);
        edtServicePrice = findViewById(R.id.edtServicePrice);
        edtServiceImage = findViewById(R.id.edtServiceImage);
        btnAddService = findViewById(R.id.btnAddService);

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gara-77a02-default-rtdb.asia-southeast1.firebasedatabase.app/");
        serviceRef = database.getReference("ServicesNew");

        btnAddService.setOnClickListener(v -> addService());
    }

    private void addService() {
        String serviceName = edtServiceName.getText().toString().trim();
        String serviceDesc = edtServiceDesc.getText().toString().trim();
        String servicePrice = edtServicePrice.getText().toString().trim();
        String serviceImage = edtServiceImage.getText().toString().trim();

        if (TextUtils.isEmpty(serviceName) || TextUtils.isEmpty(serviceDesc) || TextUtils.isEmpty(servicePrice) || TextUtils.isEmpty(serviceImage)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        int price = Integer.parseInt(servicePrice);

        String serviceId = serviceRef.push().getKey();
        ServiceModel newService = new ServiceModel(serviceId, serviceName, serviceDesc, serviceImage, price, true);

        if (serviceId != null) {
            serviceRef.child(serviceId).setValue(newService)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Dịch vụ đã được thêm thành công!", Toast.LENGTH_SHORT).show();
                            finish();  // Quay lại màn hình trước đó
                        } else {
                            Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
