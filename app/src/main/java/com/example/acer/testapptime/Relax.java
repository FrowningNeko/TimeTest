package com.example.acer.testapptime;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Relax extends Service {
    @Override
    public void onCreate() {
        MyTimer timer = new MyTimer();
        timer.Notif();
        CloseNotif();
    }

    public void CloseNotif(){
        Notif notif = new Notif();
        notif.mNotifyManager.cancel(777);
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
