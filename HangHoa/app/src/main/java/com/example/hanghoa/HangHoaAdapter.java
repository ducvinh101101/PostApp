package com.example.hanghoa;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class HangHoaAdapter extends ArrayAdapter<HangHoa> {

    public HangHoaAdapter(android.content.Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.View.inflate(getContext(), R.layout.item, null);
        }
        HangHoa hangHoa = getItem(position);
        if (hangHoa != null) {
            android.widget.TextView txtTenHang = convertView.findViewById(R.id.textView);
            android.widget.TextView txtGiaNiemYet = convertView.findViewById(R.id.textView3);
            android.widget.TextView chkGiamGia = convertView.findViewById(R.id.textView2);

            txtTenHang.setText(hangHoa.getTenHang());
            txtGiaNiemYet.setText(String.valueOf(hangHoa.getGiaNiemYet()));
            if (hangHoa.isGiamGia()) {
                chkGiamGia.setText("Giảm giá còn " + hangHoa.getGiaBan());
                chkGiamGia.setTextColor(android.graphics.Color.RED);
            } else {
                chkGiamGia.setText("Không giảm giá");
                chkGiamGia.setTextColor(android.graphics.Color.BLACK);
            }
        }
        return convertView;
    }

    public HangHoaAdapter(@NonNull Context context, int resource, @NonNull List<HangHoa> objects) {
        super(context, resource, objects);
    }
}
