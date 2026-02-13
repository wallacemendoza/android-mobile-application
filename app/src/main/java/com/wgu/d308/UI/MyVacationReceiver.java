package com.wgu.d308.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.wgu.d308.R;

public class MyVacationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        final PendingResult pendingResult = goAsync();
        new Thread(() -> {
            try {
                String channelId = "vacation_channel";
                CharSequence channelName = "Vacation Alerts";
                String contentText = intent.getStringExtra("key");
                int notificationId = intent.getIntExtra("notification_id", 0);

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);

                Notification notification = new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Vacation Alert!")
                        .setContentText(contentText)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setOngoing(true)
                        .build();

                notificationManager.notify(notificationId, notification);
            } finally {
                pendingResult.finish();
            }
        }).start();
    }
}