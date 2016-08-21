package com.example.acer.testapptime;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class CloseNotif extends Service {
    @Override
    public void onCreate() {
        stopService(new Intent(CloseNotif.this, MyTimer.class));
        stopService(new Intent(CloseNotif.this, Notif.class));
        Core.timer.cancel();
        Notif notif = new Notif();
        notif.mNotifyManager.cancel(777);
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
