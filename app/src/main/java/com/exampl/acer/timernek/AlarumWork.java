package com.exampl.acer.timernek;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;

public class AlarumWork extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        Intent intentClose = new Intent(context, CloseApp.class);
        PendingIntent pIntentClose = PendingIntent.getService(context, 0, intentClose, PendingIntent.FLAG_UPDATE_CURRENT);
        NotifWork(context, pIntentClose);
    }

    public void NotifWork(Context context, PendingIntent pIntentClose) {
        NotificationManager mNotifyManager;
        NotificationCompat.Builder mBuilder;
        mNotifyManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mNotifyManager.cancel(777);
        mBuilder.setContentTitle("Karma Timer")
                .setSmallIcon(R.drawable.ic);
        mBuilder.addAction(0, context.getText(R.string.bt_stop), pIntentClose);
        MyTimer.mHandler.sendEmptyMessage(3);
    }




    }

