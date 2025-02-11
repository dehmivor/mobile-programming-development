package com.example.intent_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy tham chiếu đến EditText và Button
        EditText nameEditText = findViewById(R.id.editTextText);
        EditText ageEditText = findViewById(R.id.editTextText2);
        Button button = findViewById(R.id.button);

        // Thiết lập sự kiện onClick cho Button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ EditText
                String name = nameEditText.getText().toString();
                String ageStr = ageEditText.getText().toString();
                int age = ageStr.isEmpty() ? 0 : Integer.parseInt(ageStr); // Nếu không nhập tuổi, mặc định là 0

                // Tạo Intent để truyền dữ liệu
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("name", name); // Truyền tên
                intent.putExtra("age", age);   // Truyền tuổi

                // Bắt đầu MainActivity2
                startActivity(intent);
            }
        });
    }
}
