package com.example.exercise_for_thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity {
    ImageView imv;
    ProgressBar progressBar;
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        imv = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
    }

    public void onDownImg(View view) {
        String imageUrl = "https://i.pinimg.com/736x/94/01/53/9401530c912b0a962398c470ceba542e.jpg";

        // Hiển thị ProgressBar trước khi tải ảnh
        progressBar.setVisibility(View.VISIBLE);
        imv.setVisibility(View.GONE);

        new Thread(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);

                // Cập nhật UI sau khi tải xong ảnh
                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    imv.setVisibility(View.VISIBLE);
                    imv.setImageBitmap(bitmap);
                });
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> progressBar.setVisibility(View.GONE));
            }
        }).start();
    }
}
