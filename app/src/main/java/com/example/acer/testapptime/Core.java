package com.example.acer.testapptime;


import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import java.util.Timer;
import java.util.TimerTask;

public class Core extends AppCompatActivity {

    long time;
   static Timer timer;
    public void Timer(){
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1://Ежесекундное обновление MainActivity
                        if (time > 60000) {
                            MainActivity.timeMin.setText("" + (time / 60000) + " мин");

                        } else {
                            MainActivity.timeMin.setText("0 минут и " + (time / 1000) + " секунд");
                        }
                        break;
                }
            }};

        long timeEnd;
        long TIME_WORK = 10000;
        long TIME_RELAX = 5000;
        long realTime = SystemClock.elapsedRealtime();
        if(MyTimer.inspec == 1){
            timeEnd = realTime + TIME_WORK;
        }
        else{
            timeEnd = realTime + TIME_RELAX;
        }
        Log.e("Core", "TimeEnd = "+ timeEnd);
        timer = new Timer();
        time = timeEnd - realTime;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(time>0) {
                    time = time - 1000;
                    mHandler.sendEmptyMessage(1);
                }
                else{
                    timer.cancel();
                }
            }
        },0,1000);
    }

}
