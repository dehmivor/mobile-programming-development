package com.example.storagedemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_usn, edt_pass;
    private CheckBox cb;
    private SharedPreferences shared_pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ánh xạ các view
        edt_usn = findViewById(R.id.edt_username);
        edt_pass = findViewById(R.id.edt_pass);
        cb = findViewById(R.id.cb_remember);
        Button btnLogin = findViewById(R.id.btn_login);

        // Thiết lập sự kiện click cho nút đăng nhập
        btnLogin.setOnClickListener(this);

        // Khởi tạo SharedPreferences
        shared_pref = getSharedPreferences("account", MODE_PRIVATE);
        editor = shared_pref.edit();

        // Tải dữ liệu đã lưu trước đó (nếu có)
        loadSavedData();
    }

    private void loadSavedData() {
        boolean isSaved = shared_pref.getBoolean("is_saved", false);
        if (isSaved) {
            String username = shared_pref.getString("username", "");
            String password = shared_pref.getString("pass", "");

            edt_usn.setText(username);
            edt_pass.setText(password);
            cb.setChecked(true);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            if (cb.isChecked()) {
                // Lưu thông tin đăng nhập
                editor.putString("username", edt_usn.getText().toString());
                editor.putString("pass", edt_pass.getText().toString());
                editor.putBoolean("is_saved", true);
            } else {
                // Xóa thông tin đăng nhập nếu checkbox không được chọn
                editor.remove("username");
                editor.remove("pass");
                editor.putBoolean("is_saved", false);
            }

            editor.apply();
        }
    }
}
