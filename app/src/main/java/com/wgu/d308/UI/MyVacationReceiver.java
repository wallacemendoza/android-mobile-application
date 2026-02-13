package com.wgu.d308.UI;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.wgu.d308.R;

public class MyVacationReceiver extends BroadcastReceiver {
    String channel_id = "vacation_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notification_id", 0);
        String message = intent.getStringExtra("key");
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(message)
                .setContentTitle("Vacation Alert!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true) // Make the notification persistent
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, n);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "Vacation Alerts";
        String description = "Notifications for upcoming vacations";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}