package com.example.baihat;

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
    ListView listView;
    SQLite sqLite;
    BaiHatAdapter baiHatAdapter;
    List<BaiHat> baiHatList;
    Button btnSort;

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
        btnSort = findViewById(R.id.btnSort);

        sqLite = new SQLite(this);

        if (sqLite.getAllKhachHang().isEmpty()){
            sqLite.addKhachHang("Hahahaha", "venh", 100,100);
            sqLite.addKhachHang("tenne","ahaha",50,40);
            sqLite.addKhachHang("khong","co",1,2);
        }
        baiHatList=sqLite.getAllKhachHang();
        double ave = aver(baiHatList);

        View headerView = getLayoutInflater().inflate(R.layout.header, listView, false);
        TextView tvAverageRating = headerView.findViewById(R.id.tvAverageTotal);
        tvAverageRating.setText(String.format("%.1f", ave));

        listView.addHeaderView(headerView, null, false);

        baiHatAdapter = new BaiHatAdapter(this,R.layout.item, baiHatList);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baiHatList.sort((bh1, bh2) -> bh1.getTen().compareTo(bh2.getTen()));
                baiHatAdapter.notifyDataSetChanged();
            }
        });
        listView.setAdapter(baiHatAdapter);
        registerForContextMenu(listView);
    }

    private double aver(List<BaiHat> baiHatList) {
        if (baiHatList.isEmpty()) {
            return 0.0;
        }
        double totalRating = 0.0;
        for (BaiHat khachHang : baiHatList) {
            totalRating += khachHang.diem();
        }
        return totalRating / baiHatList.size();
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
        BaiHat khachHang = baiHatList.get(position); // Lấy trực tiếp từ hoaDonList

        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá hàng hóa"+khachHang.getTen() +" có số tiền " + khachHang.getCaSy() + " này?" + khachHang.getId())
                    .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sqLite.deleteKhachHang(khachHang.getId());
                            baiHatList.remove(position); // Xóa khỏi danh sách
                            baiHatAdapter.notifyDataSetChanged(); // Cập nhật giao diện

                            // Cập nhật lại trung bình tổng tiền
                            double averageTotal = aver(baiHatList);
                            View headerView = listView.getChildAt(0); // Lấy header view
                            if (headerView != null) {
                                TextView tvAverageTotal = headerView.findViewById(R.id.tvAverageTotal);
                                tvAverageTotal.setText(String.format("%.1f", averageTotal));
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