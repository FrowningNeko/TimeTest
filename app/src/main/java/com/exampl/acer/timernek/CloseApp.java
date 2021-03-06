package com.exampl.acer.timernek;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CloseApp extends Service {
    @Override
    public void onCreate() {
        Log.e("CloseApp", "WAT?!");
        MyTimer.timer.cancel();
        closeAlarm();
    }
    public void closeAlarm() {

        Intent intent2 = new Intent(CloseApp.this, AlarumRelax.class);
        PendingIntent pIntentRelax = PendingIntent.getBroadcast(CloseApp.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentWork = new Intent(CloseApp.this, AlarumWork.class);
        PendingIntent pIntentWork = PendingIntent.getBroadcast(CloseApp.this, 0, intentWork, PendingIntent.FLAG_CANCEL_CURRENT);
        MyTimer.myAlarm.cancel(pIntentRelax);
        MyTimer.myAlarm.cancel(pIntentWork);
        closeService();
    }
    public void closeService() {
        stopService(new Intent(CloseApp.this, MyTimer.class));
        closeNotif();
    }

    public void closeNotif(){
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
