package com.example.acer.testapptime;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static ImageButton start;
    Button level;
    public static Button relax;
    public static TextView timeMin;
    public static int inspec = 0;
    Intent intent;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         intent = new Intent(MainActivity.this, MyTimer.class);
        start = (ImageButton)findViewById(R.id.button);
        timeMin = (TextView)findViewById(R.id.textView2);
        relax = (Button)findViewById(R.id.button2);
        level = (Button)findViewById(R.id.button3);
        relax.setEnabled(false);
        sharedPreferences = getSharedPreferences("Setting", Context.MODE_PRIVATE);
        if(!isMyServiceRunning(MyTimer.class)){
            start.setBackgroundResource(R.drawable.start);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startService(intent);
                    MyTimer();
                    ButtonStop();
                }
            });

        }
        else{
            start.setBackgroundResource(R.drawable.stop);
            inspec = MyTimer.inspec;
            if(inspec==2){
                relax.setEnabled(true);
            }
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stopService(new Intent(MainActivity.this, MyTimer.class));
                    ButtonStart();
                }
            });
        }

        relax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            MyTimer.mHandler.sendEmptyMessage(4);
                relax.setEnabled(false);
            }
        });
        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Level.class);
                startActivity(intent);
            }
        });

    }

    public void ButtonStart(){
        start.setBackgroundResource(R.drawable.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
                MyTimer();
                ButtonStop();
            }
        });
    }
    public void ButtonStop(){
        start.setBackgroundResource(R.drawable.stop);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, MyTimer.class));
                ButtonStart();
                relax.setEnabled(false);
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void MyTimer(){
        Core core = new Core();
        core.Timer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
