package com.example.acer.testapptime;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class Relax extends Service {
    @Override
    public void onCreate() {
        NotificationManager mNotifyManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.cancel(778);
        MyTimer timer = new MyTimer();
        alarmManager(timer);
    }

    public void alarmManager(MyTimer timer){
        Log.e("ШТО", "БЛЯДЬ");
        AlarmManager myAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(Relax.this, AlarumRelax.class);
        PendingIntent pIntentRelax = PendingIntent.getBroadcast(Relax.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentWork = new Intent(Relax.this, AlarumWork.class);
        PendingIntent pIntentWork = PendingIntent.getBroadcast(Relax.this, 0, intentWork, PendingIntent.FLAG_CANCEL_CURRENT);
        timer.CloseTimer();
        timer.TimerScoreFailCancel();
        SharedPreferences prefSettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        MyTimer.mHandler.sendEmptyMessage(3);
        //timer.myAlarm(myAlarm, pIntentRelax, pIntentWork, prefSettings);
        stopSelf();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
