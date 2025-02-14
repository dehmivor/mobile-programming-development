package com.example.recyclerdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Chapter> list = new ArrayList<>();
        Chapter c1 = new Chapter("Chapter one", "Overview android architecture", R.drawable.android_image_1);
        Chapter c2 = new Chapter("Chapter two", "android layout", R.drawable.android_image_2);
        Chapter c3 = new Chapter("Chapter three", "android activity", R.drawable.android_image_3);
        Chapter c4 = new Chapter("Chapter four", "android Intent", R.drawable.android_image_4);
        Chapter c5 = new Chapter("Chapter five", "android recyclerview", R.drawable.android_image_5);
        list.add(c1);
        list.add(c2);
        list.add(c3);
        list.add(c4);
        list.add(c5);
        list.add(c5);
        list.add(c5);
        list.add(c5);
        list.add(c5);

        RecyclerView rec = findViewById(R.id.rec_chapters);
        ChapterAdapter adapter = new ChapterAdapter(list);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setAdapter(adapter);

    }
}