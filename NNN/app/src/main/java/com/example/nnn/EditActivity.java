package com.example.nnn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditActivity extends AppCompatActivity {
    EditText hoTen, donGia, ngay , soNgay;
    Button btnBack, btnEdit;
    SQLite sqLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        hoTen=findViewById(R.id.editTextText11);
        donGia=findViewById(R.id.editTextText12);
        ngay=findViewById(R.id.editTextDate1);
        soNgay=findViewById(R.id.editTextText14);

        btnBack = findViewById(R.id.button3);
        btnEdit = findViewById(R.id.button4);
        sqLite = new SQLite(this);

        Intent intent2 = getIntent();
        Bundle bundle = intent2.getBundleExtra("hoadon");
        hoTen.setText(bundle.getString("name"));
        donGia.setText(String.valueOf(bundle.getInt("dongia")));
        soNgay.setText(String.valueOf(bundle.getInt("songayluutru")));
        ngay.setText(bundle.getString("day"));
        String id = bundle.getString("id");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEdit.setOnClickListener(v -> {
            try {
                String name = hoTen.getText().toString().trim();
                String date = ngay.getText().toString().trim();
                int price = Integer.parseInt(donGia.getText().toString().trim());
                int days = Integer.parseInt(soNgay.getText().toString().trim());

                if (name.isEmpty() || date.isEmpty()) {
                    Toast.makeText(EditActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                sqLite.updateHoaDon(id, name, date, price, days);
                Toast.makeText(EditActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent4);
            } catch (NumberFormatException e) {
                Toast.makeText(EditActivity.this, "Invalid number format for price or days", Toast.LENGTH_SHORT).show();
            }
        });
    }
}