package com.example.acer.testapptime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class Level extends Activity {

    TextView tvScore;
    TextView tvScoreFail;
    TextView tvKarma;
    TextView tvKarmaShop;
    int karma;
    int oldKarma;
    int karmaShop;
    int karmaFail;
    ImageView imageKarma;
    ImageButton imgInfoScore;
    ImageButton imgInfoShop;
    ImageButton imgScoreFail;
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



        tvScore.setText(String.valueOf(karma));
        progressBar.setProgress(karma);
    }
}
