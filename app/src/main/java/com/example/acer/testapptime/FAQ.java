package com.example.acer.testapptime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class FAQ extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        Button testButton = (Button)findViewById(R.id.testButton);


        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("setting", Context.MODE_PRIVATE);
                int karma = sp.getInt("Karma", 50);
                karma = karma + 10;
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("Karma", karma);
                editor.apply();
            }
        });
    }

}
