package com.example.acer.testapptime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class Level extends Activity {

    TextView tvLevel;
    TextView tvScore;
    TextView tvScoreFail;
    TextView tvKarma;
    TextView tvKarmaShop;
    int karma;
    int oldKarma;
    int karmaShop;
    int karmaFail;
    ImageView imageKarma;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    public static final String SP_SETTING = "setting";
    public static final String SP_KARMA = "Karma";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        tvScore = (TextView)findViewById(R.id.textView10);
        tvScoreFail = (TextView)findViewById(R.id.textView12);
        tvKarma = (TextView)findViewById(R.id.karma);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tvLevel = (TextView)findViewById(R.id.textView8);
        tvKarmaShop = (TextView)findViewById(R.id.karmaShop);
        imageKarma = (ImageView) findViewById(R.id.imageView7);
        sharedPreferences = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        karma = sharedPreferences.getInt(SP_KARMA, 50);
        tvKarma.setText(""+karma);
        karmaShop = sharedPreferences.getInt("KarmaShop", 0);
        karmaFail = sharedPreferences.getInt("KarmaFail", 0);
        tvScoreFail.setText(""+karmaFail);
        tvKarmaShop.setText(""+karmaShop);


        oldKarma = sharedPreferences.getInt("OldKarma", karma);
        if(oldKarma>karma){
        imageKarma.setImageResource(R.drawable.karma_false);
        }
        else {
        if(oldKarma<karma) {
            imageKarma.setImageResource(R.drawable.karma_true);
        }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("OldKarma", karma);
        editor.apply();



        tvScore.setText(String.valueOf(karma));
        progressBar.setProgress(karma);
    }
}
