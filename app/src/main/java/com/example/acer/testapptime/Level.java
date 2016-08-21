package com.example.acer.testapptime;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Level extends AppCompatActivity {

    TextView tvLevelBar;
    TextView tvLevelNew;
    TextView tvLevel;
    TextView tvScore;
    TextView tvScoreFail;
    TextView tvCoin;
    int iScore;
    TextView CoinSpend;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    public static final String SP_SETTING = "setting";
    public static final String SP_SCORE = "score";
    public static final String SP_LVL = "LVL";
    public static final String SP_SCORE_FAIL = "ScoreFail";
    public static final String SP_COIN = "Coin";
    public static final String SP_COIN_SPEND = "CoinSpend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        tvCoin = (TextView)findViewById(R.id.textView14);
        tvLevelBar = (TextView)findViewById(R.id.textView8);
        tvLevelNew = (TextView)findViewById(R.id.textView7);
        tvLevel = (TextView)findViewById(R.id.textView6);
        tvScore = (TextView)findViewById(R.id.textView10);
        tvScoreFail = (TextView)findViewById(R.id.textView12);
        CoinSpend = (TextView)findViewById(R.id.textView16);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        sharedPreferences = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        iScore = sharedPreferences.getInt(SP_SCORE, 0);
        tvScore.setText(String.valueOf(iScore));
        //tvCoin.setText(String.valueOf(sharedPreferences.getInt(SP_COIN, 0)));
        tvLevel.setText(String.valueOf(sharedPreferences.getInt(SP_LVL, 0)));
        tvLevelBar.setText(String.valueOf(sharedPreferences.getInt(SP_LVL, 0)));
        tvLevelNew.setText(String.valueOf(sharedPreferences.getInt(SP_LVL, 0)+1));
//        tvCoinSpend.setText(String.valueOf(sharedPreferences.getInt(SP_SCORE, 444)));
//        tvScoreFail.setText(String.valueOf(sharedPreferences.getInt(SP_SCORE, 444)));
        ///Исправить, когда сделаю систему монет

        switch (iScore){
            case 1:
                progressBar.setProgress(25);
                break;
            case 2:
                progressBar.setProgress(50);
                break;
            case 3:
                progressBar.setProgress(75);
                break;
        }


    }
}
