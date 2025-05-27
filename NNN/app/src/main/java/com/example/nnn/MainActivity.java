package com.example.nnn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    HoaDonAdapter hoaDonAdapter;
    List<HoaDon> list;
    Button btnSort;
    ImageButton btnSearch;
    EditText editText;
    SQLite sqLite;

    FloatingActionButton btnAdd;

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
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.floatingActionButton);
        btnSort = findViewById(R.id.btnSort);
        btnSearch = findViewById(R.id.imageButton);
        editText = findViewById(R.id.editTextSearch);

        sqLite = new SQLite(this);

        if(sqLite.getAllStudents().isEmpty()){
            sqLite.addHoaDon("Vinh", "10/01/2004", 3,2);
            sqLite.addHoaDon("Ha", "10/01/2004", 5,1);
            sqLite.addHoaDon("a", "10/01/2004", 7,5);
            sqLite.addHoaDon("hi", "10/01/2004", 1,1);
        }

        list = sqLite.getAllStudents();
        double trungBinh = tongTrungBinh(list);

        View headerView = getLayoutInflater().inflate(R.layout.hearder, listView, false);
        TextView tvAverageTotal = headerView.findViewById(R.id.tvAverageTotal);
        tvAverageTotal.setText(String.format("%.2f", trungBinh));
        listView.addHeaderView(headerView, null, false);

        hoaDonAdapter = new HoaDonAdapter(this, R.layout.item, list);

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.sort((kh1, kh2) -> Double.compare(kh2.tienHD(), kh1.tienHD()));
                hoaDonAdapter.notifyDataSetChanged();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editText.getText().toString().trim();
                list = sqLite.searchHoaDonByName(s);
                Log.d("SearchDebug", "Search query: " + s + ", Results: " + list.size());
                for (HoaDon hoaDon : list) {
                    Log.d("SearchDebug", "Found: " + hoaDon.getHoTen());
                }
                listView.setAdapter(new HoaDonAdapter(MainActivity.this, R.layout.item, list));
                listView.setVisibility(list.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        listView.setAdapter(hoaDonAdapter);
        registerForContextMenu(listView);
    }

    private double tongTrungBinh(List<HoaDon> list) {
        if (list.isEmpty()) {
            return 0;
        }
        double total = 0;
        for (HoaDon hoaDon : list) {
            total += hoaDon.tienHD();
        }
        return total / list.size();
    }

    @Override
    public void onCreateContextMenu(android.view.ContextMenu menu, android.view.View v, android.view.ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }
    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        android.widget.AdapterView.AdapterContextMenuInfo info = (android.widget.AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position - listView.getHeaderViewsCount(); // Điều chỉnh vị trí vì có header
        HoaDon hoaDon = list.get(position); // Lấy trực tiếp từ hoaDonList

        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá hóa đơn có số tiền " + hoaDon.tienHD() + " này?")
                    .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sqLite.deleteHoaDon(hoaDon.getMaHoaDon());
                            list.remove(position); // Xóa khỏi danh sách
                            hoaDonAdapter.notifyDataSetChanged(); // Cập nhật giao diện

                            // Cập nhật lại trung bình tổng tiền
                            double averageTotal = tongTrungBinh(list);
                            View headerView = listView.getChildAt(0); // Lấy header view
                            if (headerView != null) {
                                TextView tvAverageTotal = headerView.findViewById(R.id.tvAverageTotal);
                                tvAverageTotal.setText(String.format("%.2f", averageTotal));
                            }
                        }
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
            return true;
        } else if (itemId == R.id.menu_edit) {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", hoaDon.getMaHoaDon());
            bundle.putString("name", hoaDon.getHoTen());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            bundle.putString("day", hoaDon.getNgayThang());
            bundle.putInt("dongia", hoaDon.getDonGia());
            bundle.putInt("songayluutru", hoaDon.getSoNgay());
            intent.putExtra("hoadon", bundle);
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}