package com.example.acer.testapptime;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.os.SystemClock;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {


    public static Button btMain;
    Button level;
    public static TextView timeMin;
    Intent intent;
    SharedPreferences sharedPreferences;
    static ProgressBar progressBar;
    static TextView strScore;
    static TextView strScoreText;
    Button shop;

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

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        sharedPreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE);
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
    }

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
                startService(new Intent(MainActivity.this, MyTimer.class));
                loadScore();
                MyTimer.flag = 1;
                Tools();
            }
        });
    }
    public void ButtonStop(){
        btMain.setText("Стоп");
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, CloseApp.class));
                MyTimer.flag = 0;
                Tools();
            }
        });
    }

    public void ButtonWork(){
        btMain.setText("Приступить к работе");
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTimer.mHandler.sendEmptyMessage(8);
                MyTimer.flag = 1;
                Tools();
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
                Tools();
            }
        });
    }


    public void loadScore(){
        strScore = (TextView)findViewById(R.id.textView3);
        strScoreText = (TextView)findViewById(R.id.textView4);
        strScoreText.setText("Кредитов до нового уровня:");
        strScore.setText("4");
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
