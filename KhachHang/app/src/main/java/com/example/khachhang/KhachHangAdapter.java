package com.example.khachhang;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class KhachHangAdapter extends ArrayAdapter<KhachHang> {

    public KhachHangAdapter(android.content.Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.View.inflate(getContext(), R.layout.item, null);
        }
        KhachHang khachHang = getItem(position);
        if (khachHang != null) {
            android.widget.TextView txtTenKhachHang = convertView.findViewById(R.id.textView);
            android.widget.TextView txtSoDienThoai = convertView.findViewById(R.id.textView2);
            android.widget.TextView txtDiem = convertView.findViewById(R.id.textView4);
            android.widget.TextView txtNgay = convertView.findViewById(R.id.textView3);

            txtTenKhachHang.setText(khachHang.getTenKhachHang());
            txtSoDienThoai.setText(khachHang.getSoDienThoai());
            txtDiem.setText(khachHang.getDiemDanhGia() + " điểm");
            txtNgay.setText(String.valueOf(khachHang.getNgayDanhGia()));
            if(khachHang.getDiemDanhGia()<4){
                convertView.setBackgroundColor(Color.YELLOW);
            }
            else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        return convertView;
    }

    public KhachHangAdapter(@NonNull Context context, int resource, @NonNull List<KhachHang> objects) {
        super(context, resource, objects);
    }
}
