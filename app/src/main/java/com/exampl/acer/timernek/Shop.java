package com.exampl.acer.timernek;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Shop extends Activity {

    SharedPreferences spShop;
    Button shopDoubleCoin;
    Button shopMusicForest;
    Button title1;
    Button title2;
    int karma;

    TextView levelKarma;
    public static final String SP_SETTING = "setting";
    public static final String SP_KARMA = "Karma";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopDoubleCoin = (Button)findViewById(R.id.shop1);
        shopMusicForest = (Button)findViewById(R.id.shop2);
        title1 = (Button)findViewById(R.id.shop3);
        title2 = (Button)findViewById(R.id.shop4);
        spShop = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        karma = spShop.getInt(SP_KARMA, 50);
        Boolean inspecShop = spShop.getBoolean("DoubleCoin", false);
        Boolean inspecMusicForest = spShop.getBoolean("MusicForest", false);
        int inspectitle = spShop.getInt("title", 0);
        levelKarma = (TextView)findViewById(R.id.karma_level);
        levelKarma.setText(String.valueOf(karma));

        if(inspecShop){
            setBut(shopDoubleCoin);
        }
        if(inspecMusicForest){
            setBut(shopMusicForest);
        }
        switch (inspectitle){
            case 1:
                setBut(title1);
                break;
            case 2:
                setBut(title2);
                break;
        }


        shopDoubleCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cost = 10;
                if(karma >=cost){
                shopSave(1,cost);
                    showToast(true, shopDoubleCoin);
            }
            else{
                    showToast(false, null);
                }
            }

        });

        shopMusicForest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = 75;
                if(karma >= cost){
                    shopSave(2, cost);
                    showToast(true, shopMusicForest);
                }
                else{
                    showToast(false, null);
                }
            }
        });

        title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = 10;
                if (karma >= cost){
                    shopSave(3, cost);
                    showToast(true, title1);
                }
                else{
                    showToast(false, null);
                }
            }
        });

        title2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = 15;
                if (karma >= cost){
                    shopSave(4, cost);
                    showToast(true, title2);
                }
                else{
                    showToast(false, null);
                }
            }
        });
    }

    public  void shopSave(int numberShop, int cost){
        SharedPreferences.Editor editor = spShop.edit();
        int karmaShop = spShop.getInt("KarmaShop", 0);

        switch (numberShop){
            case 1:
                editor.putBoolean("DoubleCoin", true);
                break;
            case 2:
                editor.putBoolean("MusicForest", true);
                break;
            case 3:
                editor.putInt("title", 1);
                break;
            case 4:
                editor.putInt("title", 2);
                break;
        }
        karmaShop = karmaShop + cost;
        karma = karma - cost;
        levelKarma.setText(String.valueOf(karma));
        editor.putInt("KarmaShop", karmaShop);
        editor.putInt("Karma", karma);
        editor.apply();

    }

    private void showToast(Boolean i, Button b){
        if(i) {
            Toast.makeText(getApplicationContext(), getText(R.string.buy_true), Toast.LENGTH_SHORT).show();
            setBut(b);
        }
            else  Toast.makeText(getApplicationContext(), getText(R.string.need_karma_2), Toast.LENGTH_LONG).show();
    }

    private void setBut(Button b){
        b.setEnabled(false);
        b.setText("✓");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
