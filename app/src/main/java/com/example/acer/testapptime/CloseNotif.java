package com.example.acer.testapptime;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class CloseNotif extends Service {
    @Override
    public void onCreate() {
        Log.e("CloseNotif", "WAT?!");
        stopService(new Intent(CloseNotif.this, MyTimer.class));
        stopService(new Intent(CloseNotif.this, Notif.class));
        Core.timer.cancel();
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.cancel(777);
        mNotifyManager.cancel(778);
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
