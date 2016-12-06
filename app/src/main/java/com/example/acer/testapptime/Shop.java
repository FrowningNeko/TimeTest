package com.example.acer.testapptime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Shop extends Activity {

    SharedPreferences spShop;
    Button shopDoubleCoin;
    Button shop2;
    int coin;
    int lvl;
    Boolean inspecShop;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopDoubleCoin = (Button)findViewById(R.id.shop1);
        spShop = getSharedPreferences("settting", Context.MODE_PRIVATE);
        coin = spShop.getInt("Coin", 0);
        lvl = spShop.getInt("LVL", 0);
        inspecShop = spShop.getBoolean("DoubleCoin", false);

        if(inspecShop){
            shopDoubleCoin.setEnabled(false);
            shopDoubleCoin.setText("✓");
        }


        shopDoubleCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coin < 30){
                SharedPreferences.Editor editor = spShop.edit();
                shopDoubleCoin.setEnabled(false);
                shopDoubleCoin.setText("");
                editor.putBoolean("DoubleCoin", true);
                editor.apply();
                shopDoubleCoin.setText("✓");
                Toast.makeText(getApplicationContext(), "Покупка совершена!", Toast.LENGTH_SHORT).show();
            }
            else{
                    Toast.makeText(getApplicationContext(), "Не хватает монет", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}
