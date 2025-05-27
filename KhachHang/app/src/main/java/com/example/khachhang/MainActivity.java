package com.example.khachhang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLite sqLite;
    private KhachHangAdapter khachHangAdapter;
    private List<KhachHang> khachHangList;
    private Button buttonSort;
    private ListView listView;

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
        buttonSort = findViewById(R.id.btnSort);
        sqLite = new SQLite(this);

        if (sqLite.getAllKhachHang().isEmpty()) {
            sqLite.addKhachHang("Nguyen Van A", "0123456789", "10/01/2005", 5);
            sqLite.addKhachHang("Tran Thi B", "0987654321","20/12/2006" , 6);
            sqLite.addKhachHang("Le Van C", "1234567890", "10/10/2004", 8);
            sqLite.addKhachHang("Mai Van A", "0987654321", "10/03/2005", 2);
        }
        khachHangList = sqLite.getAllKhachHang();
        double averageRating = calculateAverageRating(khachHangList);

        View headerView = getLayoutInflater().inflate(R.layout.header, listView, false);
        TextView tvAverageRating = headerView.findViewById(R.id.tvAverageTotal);
        tvAverageRating.setText(String.format("%.1f", averageRating));

        listView.addHeaderView(headerView, null, false);

        khachHangAdapter = new KhachHangAdapter(this, R.layout.item, khachHangList);
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khachHangList.sort((kh1, kh2) -> Double.compare(kh2.getDiemDanhGia(), kh1.getDiemDanhGia()));
                khachHangAdapter.notifyDataSetChanged();
            }
        });
        listView.setAdapter(khachHangAdapter);
        registerForContextMenu(listView);
    }

    private double calculateAverageRating(List<KhachHang> khachHangList) {
        if (khachHangList.isEmpty()) {
            return 0.0;
        }
        double totalRating = 0.0;
        for (KhachHang khachHang : khachHangList) {
            totalRating += khachHang.getDiemDanhGia();
        }
        return totalRating / khachHangList.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.setHeaderTitle("Chọn thao tác");
    }
    @Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
        android.widget.AdapterView.AdapterContextMenuInfo info = (android.widget.AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position - listView.getHeaderViewsCount(); // Điều chỉnh vị trí vì có header
        KhachHang khachHang = khachHangList.get(position); // Lấy trực tiếp từ hoaDonList

        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá hàng hóa"+khachHang.getMaKhachHang() +" có số tiền " + khachHang.getDiemDanhGia() + " này?")
                    .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sqLite.deleteKhachHang(khachHang.getMaKhachHang());
                            khachHangList.remove(position); // Xóa khỏi danh sách
                            khachHangAdapter.notifyDataSetChanged(); // Cập nhật giao diện

                            // Cập nhật lại trung bình tổng tiền
                            double averageTotal = calculateAverageRating(khachHangList);
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
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

}