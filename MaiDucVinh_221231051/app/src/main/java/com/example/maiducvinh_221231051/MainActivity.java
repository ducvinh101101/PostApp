package com.example.maiducvinh_221231051;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    FloatingActionButton buttonAdd;
    Button btnSort;
    SQLite sqLite;
    List<MonThi> monThiList;
    MonThiAdapter monThiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toast.makeText(this,"ff",Toast.LENGTH_SHORT).show();
        listView = findViewById(R.id.listView);
        buttonAdd=findViewById(R.id.floatingActionButton);
        btnSort = findViewById(R.id.btnSort);

        sqLite = new SQLite(this);

        if(sqLite.getAllStudents().isEmpty()){
            sqLite.addHoaDon("Tin", 3, 5.6F,6.3F,0);
            sqLite.addHoaDon("Toan", 4, 5.8F,9.3F,0);
            sqLite.addHoaDon("Ly", 3, 5.6F,10F,0);
            sqLite.addHoaDon("Hoa", 3, 3.4F,2F,0);
        }

        monThiList = sqLite.getAllStudents();
        double trungBinh = tongTrungBinh(monThiList);

        View headerView = getLayoutInflater().inflate(R.layout.hearder, listView, false);
        TextView tvAverageTotal = headerView.findViewById(R.id.tvAverageTotal);
        tvAverageTotal.setText(String.format("%.1f", trungBinh));
        listView.addHeaderView(headerView, null, false);


        monThiAdapter =new MonThiAdapter(this, R.layout.item,monThiList);

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monThiList.sort((kh1, kh2) -> Float.compare(kh2.tinhDiemTK(), kh1.tinhDiemTK()));
                monThiAdapter.notifyDataSetChanged();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        listView.setAdapter(monThiAdapter);


    }

    private double tongTrungBinh(List<MonThi> monThiList) {
        if (monThiList.isEmpty()) {
            return 0;
        }
        double total = 0;
        int a = 0;
        for (MonThi hoaDon : monThiList) {
            total += hoaDon.tinhDiemTK()*hoaDon.getSoTin();
            a += hoaDon.getSoTin();
        }
        return total / a;
    }

}