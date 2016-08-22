package com.example.acer.testapptime;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
        NotificationManager mNotifyManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.cancel(778);
        MyTimer timer = new MyTimer();
        ReopenNotif(timer);
    }


    public void ReopenNotif(MyTimer timer){
        AlarmManager myAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(Relax.this, AlarumRelax.class);
        PendingIntent pIntentRelax = PendingIntent.getBroadcast(Relax.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentWork = new Intent(Relax.this, AlarumWork.class);
        PendingIntent pIntentWork = PendingIntent.getBroadcast(Relax.this, 0, intentWork, PendingIntent.FLAG_CANCEL_CURRENT);
        timer.myAlarm(myAlarm, pIntentRelax, pIntentWork);
        NotifRelax();
    }
    public void NotifRelax(){
        Intent intent = new Intent(Relax.this, Notif.class);
        startService(intent);
        stopSelf();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
