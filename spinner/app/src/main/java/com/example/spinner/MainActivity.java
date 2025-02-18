package com.example.spinner;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextView textViewResult;
    private ImageView iconImageView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        textViewResult = findViewById(R.id.textViewResult);
        iconImageView = findViewById(R.id.iconImageView);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Tạo danh sách dữ liệu cho Spinner
        List<String> items = new ArrayList<>();
        items.add("Chọn một mục");
        items.add("Mục 1");
        items.add("Mục 2");
        items.add("Mục 3");

        // Tạo Adapter cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        // Xử lý sự kiện khi chọn mục trong Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items.get(position);

                if (position == 0) {
                    textViewResult.setText("Hãy chọn một mục từ danh sách.");
                    iconImageView.setVisibility(View.GONE); // Ẩn icon khi chưa chọn mục nào
                } else {
                    textViewResult.setText("Bạn đã chọn: " + selectedItem);
                    iconImageView.setVisibility(View.VISIBLE); // Hiện icon khi chọn mục

                    // Cập nhật icon theo mục được chọn
                    if (selectedItem.equals("Mục 1")) {
                        iconImageView.setImageResource(R.drawable.ic_home);
                    } else if (selectedItem.equals("Mục 2")) {
                        iconImageView.setImageResource(R.drawable.ic_search);
                    } else if (selectedItem.equals("Mục 3")) {
                        iconImageView.setImageResource(R.drawable.ic_profile);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textViewResult.setText("Không có mục nào được chọn.");
                iconImageView.setVisibility(View.GONE);
            }
        });

        // Xử lý sự kiện khi chọn menu ở dưới cùng
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.action_home) {
                    textViewResult.setText("Bạn đã chọn: Home");
                    return true;
                } else if (itemId == R.id.action_search) {
                    textViewResult.setText("Bạn đã chọn: Search");
                    return true;
                } else if (itemId == R.id.action_profile) {
                    textViewResult.setText("Bạn đã chọn: Profile");
                    return true;
                }

                return false;
            }
        });
    }
}
