package com.wgu.d308.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.wgu.d308.R;

public class NotificationDisplayService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String channelId = intent.getStringExtra("channel_id");
        String channelName = intent.getStringExtra("channel_name");
        String contentTitle = intent.getStringExtra("content_title");
        String contentText = intent.getStringExtra("content_text");
        int notificationId = intent.getIntExtra("notification_id", 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .build();

        startForeground(notificationId, notification);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}