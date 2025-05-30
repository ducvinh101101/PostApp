package com.example.maiducvinh_221231051;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {
    SQLite sqLite;
    TextView maMon,loai;
    EditText tenMon, diemQT, diemThi, soTin;
    Button btnBack, btnAdd;

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
        maMon = findViewById(R.id.textView6);
        loai = findViewById(R.id.textView7);
        tenMon = findViewById(R.id.editTextText);
        diemQT = findViewById(R.id.editTextText2);
        diemThi = findViewById(R.id.editTextText3);
        soTin = findViewById(R.id.editTextText5);
        btnBack = findViewById(R.id.button);
        btnAdd = findViewById(R.id.button2);

        sqLite = new SQLite(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        maMon.setText(sqLite.getNextId());
        loai.setText(String.valueOf(51%3));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLite.addHoaDon(tenMon.getText().toString(),Integer.parseInt(soTin.getText().toString()),Float.valueOf(diemQT.getText().toString()),Float.valueOf(diemThi.getText().toString()),Integer.parseInt(loai.getText().toString()));
                Intent intent2 = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent2);
            }
        });
    }
}