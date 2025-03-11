package com.example.exercise_for_thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button downloadButton;
    private ProgressBar progressBar;
    private String imageUrl = "https://i.pinimg.com/736x/94/01/53/9401530c912b0a962398c470ceba542e.jpg";
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        downloadButton = findViewById(R.id.downloadButton);
        progressBar = findViewById(R.id.progressBar);

        downloadButton.setOnClickListener(v -> downloadImage());
    }

    private void downloadImage() {
        progressBar.setVisibility(View.VISIBLE);
        downloadButton.setEnabled(false);

        new Thread(() -> {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);

                handler.post(() -> {
                    imageView.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.GONE);
                    downloadButton.setEnabled(true);
                });
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> progressBar.setVisibility(View.GONE));
            }
        }).start();
    }
}
