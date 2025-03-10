package com.example.postapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private ImageButton buttonHome, buttonBook, buttonStore, buttonNotification, buttonSetting, buttonReload;
    private SharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Thiết lập xử lý WindowInsets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets; // Trả về insets để tránh lỗi
        });

        // Ánh xạ các nút điều hướng
        buttonHome = findViewById(R.id.button_home);
        buttonBook = findViewById(R.id.button_acc);
        buttonStore = findViewById(R.id.button_store);
        buttonNotification = findViewById(R.id.button_notification);
        buttonSetting = findViewById(R.id.button_setting);
        buttonReload = findViewById(R.id.button_reload);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // Mặc định hiển thị HomeFragment khi mở ứng dụng
        loadFragment(new HomeFragment());

        // Xử lý sự kiện khi bấm các nút
        buttonHome.setOnClickListener(view -> loadFragment(new HomeFragment()));
        buttonBook.setOnClickListener(view -> loadFragment(new BookFragment()));
//        buttonStore.setOnClickListener(view -> loadFragment(new StoreFragment()));
//        buttonNotification.setOnClickListener(view -> loadFragment(new NotificationFragment()));
//        buttonSetting.setOnClickListener(view -> loadFragment(new SettingFragment()));

        // Xử lý sự kiện reload
        buttonReload.setOnClickListener(v -> {
            viewModel.setButtonClicked();
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
