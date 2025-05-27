package com.example.nnn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {
    EditText hoTen, donGia, ngay , soNgay;
    Button btnBack, btnAdd;
    SQLite sqLite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sqLite = new SQLite(this);
        btnBack = findViewById(R.id.button);
        btnAdd = findViewById(R.id.button2);
        hoTen = findViewById(R.id.editTextText);
        donGia = findViewById(R.id.editTextText2);
        ngay = findViewById(R.id.editTextDate);
        soNgay = findViewById(R.id.editTextText4);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLite.addHoaDon(hoTen.getText().toString(),ngay.getText().toString(),Integer.parseInt(donGia.getText().toString()),Integer.parseInt(soNgay.getText().toString()));
                Intent intent1 = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}