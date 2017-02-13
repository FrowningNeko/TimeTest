package com.exampl.acer.timernek;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.Random;
import android.os.Handler;

public class MainActivity extends Activity {


    public static Button btMain;
    Button level;
    public static TextView timeMin;
    Intent intent;
    SharedPreferences sharedPreferences;
    static ProgressBar progressBar;
    static ImageView imageKarma1;
    static ImageView imageKarma2;
    static ImageView imageKarma3;
    static ImageView imageKarma4;
    Button shop;
    static public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         intent = new Intent(MainActivity.this, MyTimer.class);
        timeMin = (TextView)findViewById(R.id.textView2);
        TextView txTips = (TextView)findViewById(R.id.textView17);
        String tips[] = getResources().getStringArray(R.array.Tips);
        int randomTip = new Random().nextInt(21);
        txTips.setText(tips[randomTip]);
        btMain = (Button)findViewById(R.id.btMain);
        level = (Button)findViewById(R.id.button3);
        shop = (Button)findViewById(R.id.shop);
        imageKarma1 = (ImageView)findViewById(R.id.imageKarma1);
        imageKarma2 = (ImageView)findViewById(R.id.imageKarma2);
        imageKarma3 = (ImageView)findViewById(R.id.imageKarma3);
        imageKarma4 = (ImageView)findViewById(R.id.imageKarma4);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level.class);
                startActivity(intent);
            }
        });
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Shop.class));
            }
        });

        Tools();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){
                    case 1:
                        startService(new Intent(MainActivity.this, MyTimer.class));
                        MyTimer.flag = 1;
                        Tools();
                        break;
                    case 2:
                        MyTimer.mHandler.sendEmptyMessage(9);
                        MyTimer.flag = 0;
                        Tools();
                        break;

                    case 3:
                        MyTimer.mHandler.sendEmptyMessage(8);
                        MyTimer.flag = 1;
                        Tools();
                        break;
                }
            }
        };}

    public void Tools(){
        switch (MyTimer.flag){
            case 1:
                ButtonStop();
                break;
            case 2: ButtonRelax();
                break;
            case 3:ButtonWork();
                break;
            default:
                ButtonStart();
                break;
        }
    }

    public void ButtonStart(){
        btMain.setText("Старт");
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mHandler.sendEmptyMessage(1);
            }
        });
    }
    public void ButtonStop(){
        btMain.setText("Стоп");
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mHandler.sendEmptyMessage(2);
            }
        });
    }

    public void ButtonWork(){
        btMain.setText("Приступить к работе");
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mHandler.sendEmptyMessage(3);
            }
        });
    }

    public void ButtonRelax(){
        btMain.setText("Начать отдых");
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimer.mHandler.sendEmptyMessage(8);
                MyTimer.flag = 1;
                MyTimer myTimer = new MyTimer();
                myTimer.TimerScoreFailCancel();
                    MyTimer.mHandler.sendEmptyMessage(10);
                Tools();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        Tools();
        super.onResume();
    }

}
