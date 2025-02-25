package com.example.optionmenudemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "OptionMenuExample";
    private SearchView searchView;
    private String lastQuery;

    private EditText editTextInput;
    private Button btnWrite, btnRead;
    private TextView tvContent;
    private final String FILE_NAME = "myfile.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các View từ XML
        editTextInput = findViewById(R.id.editTextInput);
        btnWrite = findViewById(R.id.btnWrite);
        btnRead = findViewById(R.id.btnRead);
        tvContent = findViewById(R.id.tvContent);

        // Xử lý sự kiện ghi file
        btnWrite.setOnClickListener(v -> {
            String content = editTextInput.getText().toString().trim();
            if (!content.isEmpty()) {
                writeToInternalStorage(FILE_NAME, content);
                Toast.makeText(MainActivity.this, "Đã ghi vào file!", Toast.LENGTH_SHORT).show();
                editTextInput.setText(""); // Xóa nội dung sau khi ghi
            } else {
                Toast.makeText(MainActivity.this, "Vui lòng nhập nội dung!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện đọc file
        btnRead.setOnClickListener(v -> {
            String content = readFromInternalStorage(FILE_NAME);
            if (!content.isEmpty()) {
                tvContent.setText(content);
            } else {
                tvContent.setText("Không có dữ liệu trong file!");
            }
        });
    }

    // Hàm ghi file vào Internal Storage
    private void writeToInternalStorage(String fileName, String content) {
        try {
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
            Log.d("FileWrite", "File saved successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hàm đọc file từ Internal Storage
    private String readFromInternalStorage(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream fis = openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        // Đảm bảo icon hiển thị trong menu
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }

        // Xử lý SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        this.searchView = (SearchView) menu.findItem(R.id.menuItem_search).getActionView();
        this.searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        this.searchView.setIconifiedByDefault(true);

        return super.onCreateOptionsMenu(menu);
    }


    private boolean doSearch(String query) {
        if (query == null || query.isEmpty()) {
            return false;
        }
        this.lastQuery = query;
        Toast.makeText(this, "Tìm kiếm: " + query, Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuItem_open) {
            Toast.makeText(this, "Mở file ...", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (itemId == R.id.menuItem_delete) {
            boolean deleted = deleteFile(FILE_NAME);
            if (deleted) {
                tvContent.setText("Đã xóa file!");
                Toast.makeText(this, "File đã được xóa!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Xóa file thất bại!", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else if (itemId == R.id.menuItem_share) {
            Toast.makeText(this, "Chia sẻ nội dung ...", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (itemId == R.id.menuItem_settings) {
            Toast.makeText(this, "Mở cài đặt ...", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (itemId == R.id.menuItem_search) {
            Log.i(LOG_TAG, "onOptionsItemSelected (R.id.menuItem_search)");
            Toast.makeText(this, "Tìm kiếm ...", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
