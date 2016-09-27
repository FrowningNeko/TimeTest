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
import android.os.PowerManager;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class MyTimer extends Service {

    public static int inspec = 1; //Переход от работы к отдыху и наборот
    static int inspecCycle = 0; //Проверка количества выполнений таймера
    int score = 0;
    int scoreSP;
    int timeFail = 15000;
    int i=0;
    public static Handler mHandler;
    PendingIntent pIntentRelax;
    PendingIntent pIntentWork;
    public static final String SP_SETTING = "setting";
    public static final String SP_SCORE = "score";
    public static final String SP_LVL = "LVL";
    public static final String SP_COIN = "Coin";
    public static final String SP_SCORE_FAIL = "ScoreFail";
    static long time = 10000;
    long timeWork;
    long timeRelax;
    long realTime;
    long progressBar;
    SharedPreferences sharedPreferences;
    SharedPreferences sharPrefSettings;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyManager;
    Notification notification;
    static AlarmManager  myAlarm;
    static Timer timer;
    Timer timerFail;
    PowerManager.WakeLock wakeLock;
    static boolean b;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Игра началась!", Toast.LENGTH_SHORT).show();
        Intent intentRelax = new Intent(MyTimer.this, AlarumRelax.class);
        pIntentRelax = PendingIntent.getBroadcast(MyTimer.this, 0, intentRelax, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentWork = new Intent(MyTimer.this, AlarumWork.class);
        pIntentWork = PendingIntent.getBroadcast(MyTimer.this, 0, intentWork, PendingIntent.FLAG_CANCEL_CURRENT);
        sharedPreferences = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        sharPrefSettings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        b = sharPrefSettings.getBoolean("workAuto", false);

        scoreSP = sharedPreferences.getInt(SP_SCORE, 0);
        Intent intent1 = new Intent(MyTimer.this, CloseApp.class);
        PendingIntent pIntent2 = PendingIntent.getService(MyTimer.this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(MyTimer.this);
        mBuilder.setContentTitle("TestAppTime")//Заменить на название приложения
                .setSmallIcon(R.drawable.icon)
                .setContentText("Мяу")
                .addAction(0, "Остановить", pIntent2);
        notification = mBuilder.build();
        myAlarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 2://Окончание таймера работы
                        MainActivity.relax.setEnabled(true);
                        inspecCycle++;
                        inspec = 2;
                        stopForeground(true);
                        TimerScoreFail();
                        break;
                    case 3://Окончание таймера отдыха
                        if(inspecCycle<4){
                        MainActivity.relax.setEnabled(false);
                        inspec = 1;
                            score++;
                            MainActivity.strScore.setText(""+(4-score));
                        }
                        else{
                            levelUp();
                        }
                        break;
                    case 1://Выполнение работы таймера
                        if (time > 60000&&MainActivity.timeMin != null) {
                            MainActivity.timeMin.setText("" + (time / 60000) + " минут");

                        } else {
                            MainActivity.timeMin.setText(""+(time / 1000) + " секунд");
                        }//возможно стоит изменить время таймера и текст для быстродействия приложения
                        MainActivity.progressBar.setProgress(i);
                        if(inspec==1){
                            mBuilder.setContentText("До перерыва осталось " +((MyTimer.time)/60000)+" мин");
                        }
                        else{
                            mBuilder.setContentText("Через " +((MyTimer.time)/60000)+" мин снова работать :)");
                        }
                        mNotifyManager.notify(778, mBuilder.build());
                        break;
//                    case 4://Запуск таймера отдыха из MainActivity
////                        myAlarm();// Добавить выбор в настройках
//                        break;
                    case 5:// Сгорание очков
                        mBuilder.setContentText("Увы, очки сгорели :(");
                        stopForeground(true);
                        mNotifyManager.cancel(778);
                        mNotifyManager.notify(777, mBuilder.build());
                        MainActivity.strScore.setText("4");
                        MainActivity.timeMin.setText(":)");
                        int scoreFail = sharedPreferences.getInt("ScoreFail", 0);
                        scoreFail = scoreFail + score;
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("ScoreFail", scoreFail);
                        editor.apply();
                        score = 0;
                        //stopSelf();
                        break;
                    case 6: // Exit
                        MainActivity.timeMin.setText(":)");
                        wakeLock.release();
                        MainActivity.start.setBackgroundResource(R.drawable.start);
                        MainActivity.relax.setEnabled(false);
                        stopForeground(true);
                        break;
                    case 7:
                        timerFail.cancel();
                        break;
                    case 8:

                        break;
            }
            }
        };
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "My wakelook");
        wakeLock.acquire();
        myAlarm(myAlarm, pIntentRelax, pIntentWork, sharPrefSettings);
    }


    public void myAlarm(AlarmManager myAlarm, PendingIntent pIntentRelax, PendingIntent pIntentWork, SharedPreferences sharPrefSettings){
        String iPref = sharPrefSettings.getString("listWork", "25");
        timeWork =  Long.parseLong(iPref)*1000*60;
        String bPref = sharPrefSettings.getString("listRelax", "5");
        timeRelax = Long.parseLong(bPref)*1000*60;
        if(inspec==1){
            realTime = System.currentTimeMillis();
            myAlarm.set(AlarmManager.RTC_WAKEUP, realTime+timeWork, pIntentRelax);
            Log.e("Inspec", "Inspec = 1 "+ timeWork);
        }
        else{
            realTime = System.currentTimeMillis();
            myAlarm.set(AlarmManager.RTC_WAKEUP, realTime+timeRelax, pIntentWork);
            Log.e("Inspec", "Inspec = 2 "+ timeRelax);
        }
        Timer();
    }


    public void Timer(){
        final long timeEnd;
        long realTime = SystemClock.elapsedRealtime();
        if(inspec == 1){
            timeEnd = realTime + timeWork;
            progressBar = timeWork;
        }
        else{
            timeEnd = realTime + timeRelax;
            progressBar = timeRelax;
        }
        time = timeEnd - realTime;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(time>0) {
                    time = time - 1000;
                    i = (int)(100-(time*100/progressBar));//Получаем оставшийся процент для Progress Bar
                    mHandler.sendEmptyMessage(1);
                }
                else{
                    timer.cancel();
                }
            }
        },0,1000);
    }


    public void levelUp(){
        int lvl;
        int coin;
        lvl = sharedPreferences.getInt(SP_LVL, 0);
        inspecCycle = 0;
        lvl++;
        coin = sharedPreferences.getInt(SP_COIN, 0);
        coin = coin+10;
        score = 0;
        scoreSP = scoreSP+4;
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(SP_SCORE, scoreSP);
        edit.putInt(SP_LVL, lvl);
        edit.putInt(SP_COIN, coin);
        edit.apply();
    }

    public void CloseTimer(){
        timer.cancel();
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
        mHandler.sendEmptyMessage(6);
        super.onDestroy();
    }

    public void TimerScoreFail(){
        timerFail = new Timer();
        timerFail.schedule(new TimerTask() {
            @Override
            public void run() {
                if(timeFail>0) {
                    timeFail = timeFail - 1000;
                }
                else{
                   mHandler.sendEmptyMessage(5);
                    timerFail.cancel();
                }
            }
        },0,1000);
    }
    public void TimerScoreFailCancel(){
        mHandler.sendEmptyMessage(7);
    }

    public boolean getSettingWork(){
        return b;
    }
}
