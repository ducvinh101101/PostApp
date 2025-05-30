package com.example.maiducvinh_221231051;

import android.content.Context;
import android.graphics.Color;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MonThiAdapter extends ArrayAdapter<MonThi> {
    public MonThiAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.View.inflate(getContext(), R.layout.item, null);
        }
        MonThi monThi = getItem(position);
        if (monThi != null) {
            android.widget.TextView txtTenMon = convertView.findViewById(R.id.textView);
            android.widget.TextView txtqt = convertView.findViewById(R.id.textView2);
            android.widget.TextView txtthi = convertView.findViewById(R.id.textView4);
            android.widget.TextView txttinchi = convertView.findViewById(R.id.textView3);
            android.widget.TextView txttk = convertView.findViewById(R.id.textView5);

            txtTenMon.setText(monThi.getTenMon());
            txtqt.setText(String.valueOf(monThi.getDiemQuaTrinh()));
            txttk.setText(String.valueOf(monThi.tinhDiemTK()));
            txttinchi.setText(String.valueOf(monThi.getSoTin()));
            txtthi.setText(String.valueOf(monThi.getDiemThi()));

        }
        if (monThi.tinhDiemTK() <4) {
            convertView.setBackgroundColor(Color.YELLOW);
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }
    public MonThiAdapter(@NonNull Context context, int resource, @NonNull List<MonThi> objects) {
        super(context, resource, objects);
    }
}
