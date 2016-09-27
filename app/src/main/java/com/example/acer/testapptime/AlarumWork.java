package com.example.acer.testapptime;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class AlarumWork extends BroadcastReceiver {
    Context context;
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        Intent intentClose = new Intent(context, CloseApp.class);
        Intent intentWork = new Intent(context, Work.class);
        PendingIntent pIntentClose = PendingIntent.getService(context, 0, intentClose, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pIntentWork = PendingIntent.getService(context, 0, intentWork, 0);
        NotifWork(context, pIntentClose, pIntentWork);
    }

    public void NotifWork(Context context, PendingIntent pIntentClose, PendingIntent pIntentWork) {
        NotificationManager mNotifyManager;
        NotificationCompat.Builder mBuilder;
        mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mNotifyManager.cancel(777);
        mBuilder.setContentTitle("TestAppTime")//Заменить на название приложения
                .setSmallIcon(R.drawable.icon);
        if(MyTimer.b){
            mBuilder.addAction(0, "Приступить к работе", pIntentWork);
        }
        mBuilder.addAction(0, "Остановить", pIntentClose);
        if(!MyTimer.b) {
            MyTimer.mHandler.sendEmptyMessage(3);
        }
    }




    }

