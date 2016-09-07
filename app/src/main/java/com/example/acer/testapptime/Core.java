package com.example.acer.testapptime;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.Timer;
import java.util.TimerTask;

public class Core extends AppCompatActivity {

    long time;
    int i = 15;
    long b;
   static Timer timer;
    public void Timer(){
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1://Ежесекундное обновление MainActivity
                        if (time > 60000) {
                            MainActivity.timeMin.setText("" + (time / 60000) + " минут");

                        } else {
                            MainActivity.timeMin.setText(""+(time / 1000) + " секунд");
                        }
                        Log.e("Core", "TimeEnd = "+i);
                        MainActivity.progressBar.setProgress(i);
                        break;
                }
            }};

        final long timeEnd;
        long TIME_WORK = 10000;
        long TIME_RELAX = 5000;
        long realTime = SystemClock.elapsedRealtime();
        if(MyTimer.inspec == 1){
            timeEnd = realTime + TIME_WORK;
            b = TIME_WORK;
        }
        else{
            timeEnd = realTime + TIME_RELAX;
            b = TIME_RELAX;
        }
        timer = new Timer();
        time = timeEnd - realTime;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(time>0) {
                    time = time - 1000;
                    i = (int)(100-(time*100/b));//Получаем оставшийся процент для Progress Bar
                    mHandler.sendEmptyMessage(1);
                }
                else{
                    timer.cancel();
                }
            }
        },0,1000);
    }

}
