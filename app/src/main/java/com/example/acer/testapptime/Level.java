package com.example.acer.testapptime;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Level extends Activity {

    TextView tvLevel;
    TextView tvScore;
    TextView tvScoreFail;
    TextView tvCoin;
    int karma;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    public static final String SP_SETTING = "setting";
    public static final String SP_LVL = "LVL";
    public static final String SP_COIN = "Coin";
    public static final String SP_SCORE_FAIL = "ScoreFail";
    public static final String SP_KARMA = "Karma";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        tvCoin = (TextView)findViewById(R.id.textView14);
        tvScore = (TextView)findViewById(R.id.textView10);
        tvScoreFail = (TextView)findViewById(R.id.textView12);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        tvLevel = (TextView)findViewById(R.id.textView8);
        sharedPreferences = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);

        karma = sharedPreferences.getInt(SP_KARMA, 50);
        tvScore.setText(String.valueOf(karma));
        int level = sharedPreferences.getInt(SP_LVL, 1);
        tvCoin.setText(String.valueOf(sharedPreferences.getInt(SP_COIN, 0)));
        tvLevel.setText(String.valueOf(level));
        tvScoreFail.setText(String.valueOf(sharedPreferences.getInt(SP_SCORE_FAIL, 444)));
        progressBar.setProgress(karma);
    }
}
