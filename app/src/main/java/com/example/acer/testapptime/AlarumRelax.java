package com.example.acer.testapptime;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

public class AlarumRelax extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        Intent intent1 = new Intent(context, Relax.class);
        Intent intent2 = new Intent(context, CloseNotif.class);
        PendingIntent pIntentRelax = PendingIntent.getService(context, 0, intent1, 0);
        PendingIntent pIntentClose = PendingIntent.getService(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        NotifRelax(context, pIntentRelax, pIntentClose);
    }

    public void NotifRelax(Context context, PendingIntent pIntentRelax, PendingIntent pIntentClose) {
        NotificationManager mNotifyManager;
        NotificationCompat.Builder mBuilder;
        mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContentTitle("TestAppTime")//Заменить на название приложения
                .setSmallIcon(R.drawable.icon)
                .addAction(0, "Начать отдых", pIntentRelax)
                .addAction(0, "Остановить", pIntentClose);
        if (MyTimer.inspecCycle < 4) {
            mBuilder.setContentText("Пора отдохнуть!");
            mNotifyManager.notify(778, mBuilder.build());
        } else {
            mBuilder.setContentText("Поздравляю! Вы получили новый уровень!")
                    .setProgress(0, 0, false);
            mNotifyManager.notify(778, mBuilder.build());
        }
        sendHandlerMessage();
    }

    public void sendHandlerMessage(){
            if(MyTimer.inspec == 1){
                MyTimer.mHandler.sendEmptyMessage(2);
            }
            else{
                if(MyTimer.inspec == 2){
                    MyTimer.mHandler.sendEmptyMessage(3);
                }

        }
    }
}

