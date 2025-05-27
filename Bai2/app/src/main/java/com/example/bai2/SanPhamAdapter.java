package com.example.bai2;

import android.widget.ArrayAdapter;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {

    public SanPhamAdapter(android.content.Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        if (convertView == null) {
            convertView = android.view.View.inflate(getContext(), R.layout.item, null);
        }
        SanPham sanPham = getItem(position);
        if (sanPham != null) {
            android.widget.TextView txtTenSanPham = convertView.findViewById(R.id.textView);
            android.widget.TextView txtGiaSanPham = convertView.findViewById(R.id.textView3);
            android.widget.TextView chkKhuyenMai = convertView.findViewById(R.id.textView2);

            txtTenSanPham.setText(sanPham.getTenSanPham());
            txtGiaSanPham.setText(String.valueOf(sanPham.getGiaSanPham()));
            if(sanPham.getKhuyenMai()) {
                chkKhuyenMai.setText("Khuyến mãi giảm còn " + (sanPham.getGiaSanPham() * 0.9));
            } else {
                chkKhuyenMai.setText(" ");
            }
        }
        return convertView;
    }
}
