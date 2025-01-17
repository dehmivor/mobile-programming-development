package com.example.helloworld;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Thanh Status Bar
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));

            // Thanh Navigation Bar
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, android.R.color.black));
        }


        // Set layout
        setContentView(R.layout.layout_facebook);
    }
}
