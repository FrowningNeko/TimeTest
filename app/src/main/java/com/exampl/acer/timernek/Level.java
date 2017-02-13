package com.exampl.acer.timernek;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class Level extends Activity {

    TextView tvScore;
    TextView tvScoreFail;
    TextView tvKarma;
    TextView tvKarmaShop;
    int karma;
    int oldKarma;
    int karmaShop;
    int karmaFail;
    long realTime;
    long oldTime;
    ImageView imageKarma;
    ImageView backMob;
    ImageButton imgInfoScore;
    ImageButton imgInfoShop;
    ImageButton imgScoreFail;
    Button bt;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    ProgressBar progressBarAdd;
    public static final String SP_SETTING = "setting";
    public static final String SP_KARMA = "Karma";
    InterstitialAd mInterstitialAd;
    Thread threadAddLoading;
    Boolean isAddLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);


        tvScore = (TextView)findViewById(R.id.textView10);
        tvScoreFail = (TextView)findViewById(R.id.textView12);
        tvKarma = (TextView)findViewById(R.id.karma);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBarAdd = (ProgressBar)findViewById(R.id.progressBarAdd);
        tvKarmaShop = (TextView)findViewById(R.id.karmaShop);
        imageKarma = (ImageView) findViewById(R.id.imageView7);
        imgInfoScore = (ImageButton)findViewById(R.id.infoScore);
        imgInfoShop = (ImageButton)findViewById(R.id.infoShop);
        imgScoreFail = (ImageButton)findViewById(R.id.infoScoreFail);
        sharedPreferences = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        karma = sharedPreferences.getInt(SP_KARMA, 50);
        tvKarma.setText(""+karma);
        karmaShop = sharedPreferences.getInt("KarmaShop", 0);
        karmaFail = sharedPreferences.getInt("KarmaFail", 0);
        tvScoreFail.setText(""+karmaFail);
        tvKarmaShop.setText(""+karmaShop);
        bt = (Button)findViewById(R.id.karma_ad);

        imgInfoScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Level.this, "Карма, которую вы заработали честным трудом", Toast.LENGTH_SHORT).show();
            }
        });

        imgInfoShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Level.this, "Карма, которую вы потратили на покупки", Toast.LENGTH_SHORT).show();
            }
        });

        imgScoreFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Level.this, "Сгоревшая карма из-за вашей лени!", Toast.LENGTH_SHORT).show();
            }
        });

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
        realTime = System.currentTimeMillis();
        oldTime = sharedPreferences.getLong("realTime", realTime);

        if(oldTime>realTime){
            adsBlock();
        }

        tvScore.setText(String.valueOf(karma));
        progressBar.setProgress(karma);
        backMob = (ImageView)findViewById(R.id.backMob);
        backMob.setVisibility(View.GONE);
        progressBarAdd.setVisibility(View.GONE);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2759098232123964/2167740534");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                int karma = sharedPreferences.getInt("Karma", 50);
                karma = karma + 3;
                long i = System.currentTimeMillis()+300000;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Karma", karma)
                .putInt("OldKarma", karma)
                .putLong("realTime", i);
                editor.apply();
                imageKarma.setImageResource(R.drawable.karma_true);
                tvKarma.setText(""+karma);
                adsBlock();
                requestNewInterstitial();
            }
        });

        final Handler mHandler = new android.os.Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        if(mInterstitialAd.isLoaded()) {
                            progressBarAdd.setVisibility(View.GONE);
                            backMob.setVisibility(View.GONE);
                            isAddLoaded = true;
                            mInterstitialAd.show();
                        }
                break;
                }
            }
        };



        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestNewInterstitial();
               isAddLoaded = false;
                progressBarAdd.setVisibility(View.VISIBLE);
                backMob.setVisibility(View.VISIBLE);
                threadAddLoading = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            while (!isAddLoaded){
                                mHandler.sendEmptyMessage(1);
                                Thread.sleep(2000);
                            };

                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        }



                    }
                });
                threadAddLoading.start();
            }
        });}

        private void requestNewInterstitial() {
            AdRequest adRequest = new AdRequest.Builder()
                    .build();

            mInterstitialAd.loadAd(adRequest);
        }

    private void adsBlock(){
        bt.setEnabled(false);
        realTime = System.currentTimeMillis();
        oldTime = sharedPreferences.getLong("realTime", realTime);
        bt.setText("Функция станет доступна вновь через "+((oldTime-realTime)/60000+1)+" мин");
    }


    }


