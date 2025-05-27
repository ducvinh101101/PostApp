package com.example.bai1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SQLite dataHelper;

    FloatingActionButton add_button;
    Button button;
    HoaDonAdapter hoaDonAdapter; // Biến instance cho adapter
    List<HoaDon> hoaDonList;
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
        dataHelper = new SQLite(this);
        dataHelper.deleteAllHoaDon();
        if(dataHelper.getAllStudents().isEmpty()) {
            dataHelper.addHoaDon("Nguyễn Văn a", "10/10/2000", 100000, 3);
            dataHelper.addHoaDon("Nguyễn Văn b", "11/11/2001", 20, 4);
            dataHelper.addHoaDon("Nguyễn Văn c", "12/12/2002", 30000, 5);
            dataHelper.addHoaDon("Nguyễn", "13/13/2003", 4000000, 6);
        }
        listView = findViewById(R.id.listView);
        add_button = findViewById(R.id.floatingActionButton);
        button = findViewById(R.id.btnSort);

        // Tính toán trung bình tổng tiền của các hóa đơn
        hoaDonList = dataHelper.getAllStudents();
        double averageTotal = calculateAverageTotal(hoaDonList);

        // Thêm header view vào ListView
        View headerView = getLayoutInflater().inflate(R.layout.hearder_view, listView, false);
        TextView tvAverageTotal = headerView.findViewById(R.id.tvAverageTotal);
        tvAverageTotal.setText(String.format("%.2f", averageTotal));
        listView.addHeaderView(headerView, null, false);

        hoaDonAdapter = new HoaDonAdapter(
                this,
                R.layout.item,
                hoaDonList
        );

        button.setOnClickListener(v -> {
            Collections.sort(hoaDonList, new Comparator<HoaDon>() {
                @Override
                public int compare(HoaDon h1, HoaDon h2) {
                    return Integer.compare(h1.getTongTien(), h2.getTongTien());
                }
            });

            hoaDonAdapter.notifyDataSetChanged();
        });
        listView.setAdapter(hoaDonAdapter);
        add_button.setOnClickListener(v -> {
            Intent addActivity = new Intent(MainActivity.this, AddActivity.class);
            startActivity(addActivity);
        });
        registerForContextMenu(listView);
    }
    private double calculateAverageTotal(List<HoaDon> hoaDonList) {
        if (hoaDonList.isEmpty()) {
            return 0;
        }
        double total = 0;
        for (HoaDon hoaDon : hoaDonList) {
            total += hoaDon.getTongTien();
        }
        return total / hoaDonList.size();
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
        HoaDon hoaDon = hoaDonList.get(position); // Lấy trực tiếp từ hoaDonList

        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá hóa đơn có số tiền " + hoaDon.getTongTien() + " này?")
                    .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataHelper.deleteHoaDon(hoaDon.getMaHoaDon());
                            hoaDonList.remove(position); // Xóa khỏi danh sách
                            hoaDonAdapter.notifyDataSetChanged(); // Cập nhật giao diện

                            // Cập nhật lại trung bình tổng tiền
                            double averageTotal = calculateAverageTotal(hoaDonList);
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
            bundle.putString("name", hoaDon.getTenKhachHang());
            bundle.putString("day", hoaDon.getNgayLap());
            bundle.putInt("dongia", hoaDon.getDonGia());
            bundle.putInt("songayluutru", hoaDon.getSoNgayLuuTru());
            intent.putExtra("hoadon", bundle);
            startActivity(intent);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }
}