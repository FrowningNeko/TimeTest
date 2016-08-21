package com.example.acer.testapptime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Notif extends Service {
    long timeEnd;
    long realTime;
    int i=0;
    int timeMax;
    int inspec;
    long TIME_WORK = 10000;
    long TIME_RELAX = 5000;

    NotificationManager mNotifyManager;

    @Override
    public void onCreate(){
        Intent intent1 = new Intent(Notif.this, CloseNotif.class);
        PendingIntent pIntent2 = PendingIntent.getService(Notif.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification;
        mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Notif.this);
        mBuilder.setContentTitle("TestAppTime")//Заменить на название приложения
                .setSmallIcon(R.drawable.icon)
                .setContentText("Мяу")
                .addAction(0, "Остановить", pIntent2);
        notification = mBuilder.build();
        startForeground(777, notification);
        ShowNotifTimer(mNotifyManager, mBuilder);
    }

    public void ShowNotifTimer(final NotificationManager mNotifyManager, final NotificationCompat.Builder mBuilder){
        final Timer timer = new Timer();
        MyTimer myTimer = new MyTimer();
        timeMax = myTimer.timeMax;
        inspec = myTimer.inspec;
        realTime = System.currentTimeMillis();
        if(MyTimer.inspec == 1){
            timeEnd = realTime + TIME_WORK;
        }
        else{
            timeEnd = realTime + TIME_RELAX;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
        if(i < timeMax) {
            i = i + 1000;
            if(inspec==1){
                mBuilder.setContentText("До перерыва осталось " +((timeEnd-realTime)/60000)+" мин");
            }
            else{
                mBuilder.setContentText("Через " +((timeEnd-realTime)/60000)+" мин снова работать :)");
            }

            mNotifyManager.notify(777, mBuilder.build());}
        else{
                timer.cancel();
            stopSelf();
            }}}, 0, 1000);//изменить на 30000 после релиза
        }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
