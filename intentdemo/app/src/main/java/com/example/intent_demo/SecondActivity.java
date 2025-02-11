package com.example.intent_demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Nhận dữ liệu từ Intent
        String name = getIntent().getStringExtra("name");
        int age = getIntent().getIntExtra("age", 0); // Mặc định nếu không có giá trị là 0

        // Hiển thị dữ liệu trong TextView
        TextView textView = findViewById(R.id.textView);
        textView.setText("Name: " + name + "\nAge: " + age);
    }
}
