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
    int karma;
    Boolean inspecShop;
    Boolean inscecMusicForest;
    TextView levelKarma;
    public static final String SP_SETTING = "setting";
    public static final String SP_KARMA = "Karma";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopDoubleCoin = (Button)findViewById(R.id.shop1);
        shopMusicForest = (Button)findViewById(R.id.shop2);
        spShop = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        karma = spShop.getInt(SP_KARMA, 50);
        inspecShop = spShop.getBoolean("DoubleCoin", false);
        inscecMusicForest = spShop.getBoolean("MusicForest", false);
        levelKarma = (TextView)findViewById(R.id.karma_level);
        levelKarma.setText(String.valueOf(karma));

        if(inspecShop){
            shopDoubleCoin.setEnabled(false);
            shopDoubleCoin.setText("✓");
        }
        if(inscecMusicForest){
            shopMusicForest.setEnabled(false);
            shopMusicForest.setText("✓");
        }


        shopDoubleCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(karma >=65){
                shopDoubleCoin.setEnabled(false);
                shopDoubleCoin.setText("✓");
                int numberShop;
                numberShop = 1;
                shopSave(numberShop);
                levelKarma.setText(String.valueOf(karma));
                Toast.makeText(getApplicationContext(), "Покупка совершена!", Toast.LENGTH_SHORT).show();
            }
            else{
                    Toast.makeText(getApplicationContext(), "Не хватает кармы", Toast.LENGTH_SHORT).show();
                }
            }

        });

        shopMusicForest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(karma >= 80){
                    shopMusicForest.setEnabled(false);
                    shopMusicForest.setText("✓");
                    int numberShop = 2;
                    shopSave(numberShop);
                    levelKarma.setText(String.valueOf(karma));
                    Toast.makeText(getApplicationContext(), "Покупка совершена!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Не хватает кармы", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  void shopSave(int numberShop){
        SharedPreferences.Editor editor = spShop.edit();
        int karmaShop = spShop.getInt("KarmaShop", 0);

        switch (numberShop){
            case 1:
                editor.putBoolean("DoubleCoin", true);
                karmaShop = karmaShop+10;
                karma = karma - 10;
                break;
            case 2:
                editor.putBoolean("MusicForest", true);
                karmaShop = karmaShop+50;
                karma = karma - 50;
                break;
        }
        editor.putInt("KarmaShop", karmaShop);
        editor.putInt("Karma", karma);
        editor.apply();

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
