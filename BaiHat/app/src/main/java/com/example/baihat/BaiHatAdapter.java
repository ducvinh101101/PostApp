package com.example.baihat;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class BaiHatAdapter extends ArrayAdapter<BaiHat> {
    public BaiHatAdapter(android.content.Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.View.inflate(getContext(), R.layout.item, null);
        }
        BaiHat baiHat = getItem(position);
        if (baiHat != null) {
            android.widget.TextView txtTenKhachHang = convertView.findViewById(R.id.textView);
            android.widget.TextView txtSoDienThoai = convertView.findViewById(R.id.textView2);
            android.widget.TextView txtDiem = convertView.findViewById(R.id.textView4);
            android.widget.TextView txtNgay = convertView.findViewById(R.id.textView3);
            android.widget.TextView txtShare = convertView.findViewById(R.id.textView5);

            txtTenKhachHang.setText(baiHat.getTenBai());
            txtSoDienThoai.setText(baiHat.getCaSy());
            txtDiem.setText(String.valueOf(baiHat.diem()));
            txtNgay.setText(String.valueOf(baiHat.getSoLike()));
            txtShare.setText(String.valueOf(baiHat.getSoShare()));
            if (baiHat.diem() > 160) {
                convertView.setBackgroundColor(Color.YELLOW);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        return convertView;
    }
    public BaiHatAdapter(@NonNull Context context, int resource, @NonNull List<BaiHat> objects) {
        super(context, resource, objects);
    }
}
