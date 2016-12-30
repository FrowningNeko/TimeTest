package com.example.acer.testapptime;

import android.content.Context;
import android.content.Intent;
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
    int karma;
    Boolean inspecShop;
    TextView levelKarma;
    public static final String SP_SETTING = "setting";
    public static final String SP_KARMA = "Karma";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopDoubleCoin = (Button)findViewById(R.id.shop1);
        spShop = getSharedPreferences(SP_SETTING, Context.MODE_PRIVATE);
        karma = spShop.getInt(SP_KARMA, 50);
        inspecShop = spShop.getBoolean("DoubleCoin", false);
        levelKarma = (TextView)findViewById(R.id.karma_level);
        levelKarma.setText(String.valueOf(karma));

        if(inspecShop){
            shopDoubleCoin.setEnabled(false);
            shopDoubleCoin.setText("✓");
        }


        shopDoubleCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(karma >65){
                SharedPreferences.Editor editor = spShop.edit();
                shopDoubleCoin.setEnabled(false);
                shopDoubleCoin.setText("");
                int karmaShop = spShop.getInt("KarmaShop", 0);
                karmaShop = karmaShop+10;
                karma = karma - 10;
                editor.putInt("KarmaShop", karmaShop);
                editor.putBoolean("DoubleCoin", true);
                editor.putInt("Karma", karma);
                editor.apply();
                shopDoubleCoin.setText("✓");
                levelKarma.setText(String.valueOf(karma));
                Toast.makeText(getApplicationContext(), "Покупка совершена!", Toast.LENGTH_SHORT).show();
            }
            else{
                    Toast.makeText(getApplicationContext(), "Не хватает кармы", Toast.LENGTH_SHORT).show();
                }
            }

        });
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
