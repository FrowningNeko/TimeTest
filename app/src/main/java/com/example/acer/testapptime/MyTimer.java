package com.example.acer.testapptime;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MyTimer extends Service {

    public static int inspec = 1; //Переход от работы к отдыху и наборот
   static int inspecCycle = 0; //Проверка количества выполнений таймера
    int score = 0;
    int timeMax = 10000;
    long realTime;
    long timeWork = 10000;
    long timeEnd = 0;
    long timeRelax = 5000;
    static int i = 0;
    public static Handler mHandler;
    PendingIntent pIntent3;
    public static final String SP_SETTING = "setting";
    public static final String SP_SCORE = "score";
    public static final String SP_LVL = "LVL";
    public static final String SP_COIN = "Coin";
    public static final String SP_TIME = "Time";
    SharedPreferences sharedPreferences;
   AlarmManager myAlarm;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Игра началась!", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(MyTimer.this, CloseNotif.class);
        Intent intent2 = new Intent(MyTimer.this, Alarum.class);
        PendingIntent pIntent2 = PendingIntent.getService(MyTimer.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        pIntent3 = PendingIntent.getBroadcast(MyTimer.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        sharedPreferences = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        myAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        score = sharedPreferences.getInt(SP_SCORE, 0);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 2://Окончание таймера работы
                        MainActivity.relax.setEnabled(true);
                        inspecCycle++;
                        inspec = 2;
                        break;
                    case 3://Окончание таймера отдыха
                        if(inspecCycle<4){
                        MainActivity.relax.setEnabled(false);
                        inspec = 1;
                            score++;
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putInt("score", score);
                            edit.apply();
                        myAlarm();
                        }
                        else{
                            levelUp();
                        }
                        break;
//                    case 4://Запуск таймера отдыха из MainActivity
////                        myAlarm();// Добавить выбор в настройках
//                        Notif();
//                        break;
            }
            }
        };
        myAlarm();

    }


    public void myAlarm(){
        if(inspec==1){
            realTime = System.currentTimeMillis();
            timeMax = (int)(timeWork);
            myAlarm.set(AlarmManager.RTC_WAKEUP, realTime+timeWork, pIntent3);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            timeEnd = realTime+timeWork;
            edit.putLong(SP_TIME, timeEnd);
            edit.apply();
        }
        else{
            Log.e("MyTimer", "Я ТУТ!");
            realTime = System.currentTimeMillis();
            timeMax = (int)(timeRelax);
            myAlarm.set(AlarmManager.RTC_WAKEUP, realTime+timeRelax, pIntent3);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            timeEnd = realTime+timeRelax;
            edit.putLong(SP_TIME, timeEnd);
            edit.apply();
        }
        Notif();
    }

    public void Notif(){
        Intent intent = new Intent(MyTimer.this, Notif.class);
        startService(intent);
    }

    public void levelUp(){
        int lvl;
        int Coin;
        lvl = sharedPreferences.getInt(SP_LVL, 0);
        inspecCycle = 0;
        lvl++;
        Coin = sharedPreferences.getInt(SP_COIN, 0);
        Coin ++;
        score = 0;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(SP_LVL, lvl);
        edit.putInt(SP_COIN, Coin);
        edit.putInt(SP_SCORE, score);
        edit.apply();
    }

        @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onDestroy()
    {
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();
        Log.e("MyTimer", "Я...Я умер Q_Q");
        stopForeground(true);
        myAlarm.cancel(pIntent3);
        i = timeMax;//Аве костылям!!!
        MainActivity.start.setBackgroundResource(R.drawable.start);
        MainActivity.relax.setEnabled(false);
        super.onDestroy();
    }
}
