package com.example.acer.testapptime;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Relax extends Service {
    @Override
    public void onCreate() {
        Notif();
    }

    public void Notif(){
        Intent intent = new Intent(Relax.this, Notif.class);
        startService(intent);
        CloseNotif();
    }

    public void CloseNotif(){
        NotificationManager mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.cancel(777);
        MyTimer timer = new MyTimer();
        ReopenNotif(timer);
    }

    public void ReopenNotif(MyTimer timer){
        Log.e("Relax", "Всё норм, я тут");
        timer.myAlarm();
        stopSelf();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
