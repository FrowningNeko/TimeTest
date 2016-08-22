package com.example.acer.testapptime;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class MyTimer extends Service {

    public static int inspec = 1; //Переход от работы к отдыху и наборот
   static int inspecCycle = 0; //Проверка количества выполнений таймера
    int score = 0;
    long realTime;
    public static Handler mHandler;
    PendingIntent pIntentRelax;
    public static final String SP_SETTING = "setting";
    public static final String SP_SCORE = "score";
    public static final String SP_LVL = "LVL";
    public static final String SP_COIN = "Coin";
    public static final String SP_TIME = "Time";
    SharedPreferences sharedPreferences;
    static AlarmManager  myAlarm;
    PendingIntent pIntentWork;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Игра началась!", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(MyTimer.this, AlarumRelax.class);
        pIntentRelax = PendingIntent.getBroadcast(MyTimer.this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentWork = new Intent(MyTimer.this, AlarumWork.class);
        pIntentWork = PendingIntent.getBroadcast(MyTimer.this, 0, intentWork, PendingIntent.FLAG_CANCEL_CURRENT);
        sharedPreferences = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        score = sharedPreferences.getInt(SP_SCORE, 0);
        myAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
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
                        myAlarm(myAlarm, pIntentRelax, pIntentWork);
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
        myAlarm(myAlarm, pIntentRelax, pIntentWork);
        Notif();
    }


    public void myAlarm(AlarmManager myAlarm, PendingIntent pIntentRelax, PendingIntent pIntentWork){
        if(inspec==1){
            realTime = System.currentTimeMillis();
            long timeWork = 10000;
            myAlarm.set(AlarmManager.RTC_WAKEUP, realTime+timeWork, pIntentRelax);
        }
        else{
            realTime = System.currentTimeMillis();
            long timeRelax = 5000;
            myAlarm.set(AlarmManager.RTC_WAKEUP, realTime+timeRelax, pIntentWork);
        }
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
        stopForeground(true);
        MainActivity.start.setBackgroundResource(R.drawable.start);
        MainActivity.relax.setEnabled(false);
        super.onDestroy();
    }
}
