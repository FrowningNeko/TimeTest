package com.example.acer.testapptime;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

public class AlarumWork extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        Intent intent2 = new Intent(context, CloseNotif.class);
        PendingIntent pIntent2 = PendingIntent.getService(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        NotifWork(context, pIntent2);
    }

    public void NotifWork(Context context, PendingIntent pIntent2) {
        NotificationManager mNotifyManager;
        NotificationCompat.Builder mBuilder;
        mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("TestAppTime")//Заменить на название приложения
                .setSmallIcon(R.drawable.icon)
                .addAction(0, "Остановить", pIntent2);
        if (MyTimer.inspecCycle < 4) {
            mBuilder.setContentText("Пора работать! :)");
            mNotifyManager.notify(778, mBuilder.build());
        } else {
            mBuilder.setContentText("Поздравляю! Вы получили новый уровень!")
                    .setProgress(0, 0, false);
            mNotifyManager.notify(778, mBuilder.build());
            sendHandlerMessage(context);
        }
    }

    public void sendHandlerMessage(Context context) {
        if (MyTimer.inspec == 1) {
            MyTimer.mHandler.sendEmptyMessage(2);
        } else {
            MyTimer.mHandler.sendEmptyMessage(3);
        }
        startRelax(context);
    }

    public void startRelax(Context context){
        Intent intent1 = new Intent(context, Relax.class);
        context.startService(intent1);
        }
    }

