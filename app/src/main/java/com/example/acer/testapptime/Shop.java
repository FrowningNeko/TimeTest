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
import android.widget.Toast;

public class Shop extends Activity {

    SharedPreferences spShop;
    Button shopDoubleCoin;
    int coin;
    int lvl;
    int karma;
    Boolean inspecShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopDoubleCoin = (Button)findViewById(R.id.shop1);
        spShop = getSharedPreferences("settting", Context.MODE_PRIVATE);
        karma = spShop.getInt("Karma", 50);
        lvl = spShop.getInt("LVL", 0);
        inspecShop = spShop.getBoolean("DoubleCoin", false);

        if(inspecShop){
            shopDoubleCoin.setEnabled(false);
            shopDoubleCoin.setText("✓");
        }


        shopDoubleCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(karma >49){//После тестов исправить на 60
                SharedPreferences.Editor editor = spShop.edit();
                shopDoubleCoin.setEnabled(false);
                shopDoubleCoin.setText("");
                editor.putBoolean("DoubleCoin", true);
                editor.apply();
                shopDoubleCoin.setText("✓");
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

        menu.add(0, 1, 0, "")
                .setTitle(String.valueOf(karma))
                .setShowAsAction(1);
        menu.add(0, 2, 0, "")//Исправить на что-нибудь зеленое
                .setIcon(R.drawable.coin)
                .setTitle("")
                .setEnabled(false)
                .setShowAsAction(1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
