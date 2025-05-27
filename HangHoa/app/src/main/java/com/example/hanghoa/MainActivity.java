package com.example.hanghoa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    private ListView listView;
    private HangHoaAdapter hangHoaAdapter;
    private List<HangHoa> hangHoaList;
    private Button buttonSort;
    private SQLite sqLite;

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

        if(sqLite.getAllHangHoa().isEmpty()){
            sqLite.addHangHoa("samsung",300000,true);
            sqLite.addHangHoa("apple", 400000,false);
            sqLite.addHangHoa("huawei", 200000, false);
        }

        hangHoaList = sqLite.getAllHangHoa();
        double avage = caculaterHoDon(hangHoaList);

        View header = getLayoutInflater().inflate(R.layout.header,listView,false);
        TextView tv =header.findViewById(R.id.tvAverageTotal);
        tv.setText(String.format("%.2f",avage));
        listView.addHeaderView(header,null,false);


        hangHoaAdapter =new HangHoaAdapter(this,R.layout.item, hangHoaList);
        buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangHoaList.sort((o1, o2) -> Double.compare(o1.getGiaBan(), o2.getGiaBan()));
                hangHoaAdapter.notifyDataSetChanged();
            }
        });

        listView.setAdapter(hangHoaAdapter);

        registerForContextMenu(listView);

    }

    private double caculaterHoDon(List<HangHoa> hangHoaList) {
        if(hangHoaList.isEmpty()){
            return  0;
        }
        double total=0;
        for (HangHoa hangHoa: hangHoaList){
            total = total+ hangHoa.getGiaBan();
        }
        return total/hangHoaList.size();
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
        HangHoa hangHoa = hangHoaList.get(position); // Lấy trực tiếp từ hoaDonList

        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá hàng hóa"+hangHoa.getMaHang() +" có số tiền " + hangHoa.getGiaBan() + " này?")
                    .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sqLite.deleteHangHoa(hangHoa.getMaHang());
                            hangHoaList.remove(position); // Xóa khỏi danh sách
                            hangHoaAdapter.notifyDataSetChanged(); // Cập nhật giao diện

                            // Cập nhật lại trung bình tổng tiền
                            double averageTotal = caculaterHoDon(hangHoaList);
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