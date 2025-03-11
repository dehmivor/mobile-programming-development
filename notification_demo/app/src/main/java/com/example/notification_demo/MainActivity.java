package com.example.notification_demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

public class MainActivity extends AppCompatActivity {
    public final String FIRST_CHANNEL = "first_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Nhận phản hồi từ RemoteInput nếu có
        Bundle b = RemoteInput.getResultsFromIntent(getIntent());
        if (b != null) {
            String s = b.getString("message_content");
            if (s != null) {
                Toast.makeText(this, "Bạn đã trả lời: " + s, Toast.LENGTH_LONG).show();
            }
        }

        // Kiểm tra và yêu cầu quyền trên Android 13+ nếu chưa cấp
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
        }
    }

    @SuppressLint("MissingPermission")
    public void onSendNotifi(View view) {
        NotificationManager manager = getSystemService(NotificationManager.class);

        // Intent mở ứng dụng khi bấm vào thông báo
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 100, i, PendingIntent.FLAG_MUTABLE);

        // Intent xử lý phản hồi ngay trong cùng file
        Intent replyIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent replyPendingIntent = PendingIntent.getActivity(this, 101, replyIntent, PendingIntent.FLAG_MUTABLE);

        // RemoteInput để nhập tin nhắn
        RemoteInput rm = new RemoteInput.Builder("message_content")
                .setLabel("Nhập tin nhắn của bạn")
                .build();

        // Action cho nút "Trả lời"
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                android.R.drawable.ic_dialog_info, "Trả lời", replyPendingIntent)
                .addRemoteInput(rm)
                .build();

        // Tạo kênh thông báo nếu chạy trên Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(FIRST_CHANNEL, "Thông báo", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        // Kiểm tra quyền trước khi gửi thông báo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return; // Không có quyền, không gửi thông báo
        }

        // Tạo thông báo
        Notification n = new NotificationCompat.Builder(this, FIRST_CHANNEL)
                .setContentTitle("Thông báo")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentText("Bạn vừa nhận được 1 BTC từ LoanBT")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .addAction(action) // Thêm action trả lời
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        // Gửi thông báo
        manager.notify(1, n);
    }

    // Lớp xử lý phản hồi ngay trong MainActivity
    public static class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = RemoteInput.getResultsFromIntent(intent);
            if (b != null) {
                String message = b.getString("message_content");
                if (message != null) {
                    Toast.makeText(context, "Bạn đã trả lời: " + message, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
