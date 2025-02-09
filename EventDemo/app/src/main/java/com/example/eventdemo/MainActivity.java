package com.example.eventdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        findViewById(R.id.ln_touch).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent m) {
                Log.d("Main_LÒG_EVENT", "pointer count: " + m.getPointerCount());
                Log.d("Main_LÒG_EVENT", "X: " + m.getX(0));
                Log.d("Main_LÒG_EVENT", "y: " + m.getY());
                if(m.getAction()==MotionEvent.ACTION_DOWN){
                    Log.d("Main_LOG_EVENT","action down");
                } else if(m.getAction()==MotionEvent.ACTION_MOVE){
                    Log.d("Main_LOG_EVENT","action move");
                } else if(m.getAction()==MotionEvent.ACTION_UP){
                    Log.d("Main_LOG_EVENT","action UP");
                }

                return false;
            }
        });

    }
}
