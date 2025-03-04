package com.example.storagedemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import com.example.storagedemo.DatabaseManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_usn, edt_pass;
    private CheckBox cb;
    private SharedPreferences shared_pref;
    private SharedPreferences.Editor editor;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // **1. Khởi tạo DatabaseManager**
        dbManager = new DatabaseManager(this);
        dbManager.closeDatabase(); // Đóng database trước khi mở Database Inspector

        // **2. Chèn dữ liệu vào database**
        dbManager.insertUser("Nguyen Van A", 25);
        dbManager.insertUser("Tran Van B", 30);

        // **3. Ánh xạ các view**
        edt_usn = findViewById(R.id.edt_username);
        edt_pass = findViewById(R.id.edt_pass);
        cb = findViewById(R.id.cb_remember);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnShowUsers = findViewById(R.id.btn_show_users); // Nút mới thêm

        // **4. Thiết lập sự kiện click**
        btnLogin.setOnClickListener(this);
        btnShowUsers.setOnClickListener(this);

        // **5. Khởi tạo SharedPreferences**
        shared_pref = getSharedPreferences("account", MODE_PRIVATE);
        editor = shared_pref.edit();

        // **6. Tải dữ liệu đã lưu trước đó**
        loadSavedData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDatabase(); // Đóng database khi Activity bị hủy
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
                // **Lưu thông tin đăng nhập**
                editor.putString("username", edt_usn.getText().toString());
                editor.putString("pass", edt_pass.getText().toString());
                editor.putBoolean("is_saved", true);
            } else {
                // **Xóa thông tin đăng nhập**
                editor.remove("username");
                editor.remove("pass");
                editor.putBoolean("is_saved", false);
            }
            editor.apply();
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btn_show_users) {
            // **Lấy danh sách người dùng từ database**
            List<String> users = dbManager.getAllUsers();

            // **Hiển thị danh sách bằng Toast**
            if (!users.isEmpty()) {
                StringBuilder userList = new StringBuilder();
                for (String user : users) {
                    userList.append(user).append("\n");
                }
                Toast.makeText(this, userList.toString(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Không có dữ liệu người dùng!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
