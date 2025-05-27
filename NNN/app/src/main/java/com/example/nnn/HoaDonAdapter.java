package com.example.nnn;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class HoaDonAdapter extends ArrayAdapter<HoaDon> {
    public HoaDonAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.View.inflate(getContext(), R.layout.item, null);
        }
        HoaDon hoaDon = getItem(position);
        if (hoaDon != null) {
            android.widget.TextView txtTenKhachHang = convertView.findViewById(R.id.textView4);
            android.widget.TextView txtNgayLap = convertView.findViewById(R.id.textView7);
            android.widget.TextView txtTongTien = convertView.findViewById(R.id.textView8);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            txtTenKhachHang.setText(hoaDon.getHoTen());
            txtNgayLap.setText(hoaDon.getNgayThang());
            txtTongTien.setText(String.valueOf(hoaDon.tienHD()));
        }
        if (hoaDon.tienHD() > 3000000) {
            convertView.setBackgroundColor(Color.YELLOW);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }
    public HoaDonAdapter(@NonNull Context context, int resource, @NonNull List<HoaDon> objects) {
        super(context, resource, objects);
    }
}
