package elia.shapira.elimorse;

import android.app.Application;
import android.app.NotificationManager;

public class NotificationChannel extends Application {

    public static final String CHANNEL_1_ID = "EliMorse_app_channel_1";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        android.app.NotificationChannel channel1 = new android.app.NotificationChannel(
                CHANNEL_1_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
        );
        channel1.setDescription(getString(R.string.notification_channel_description));
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel1);
    }
}
