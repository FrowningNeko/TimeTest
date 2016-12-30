package com.example.acer.testapptime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        getActionBar().setIcon(new ColorDrawable(0));
        Preference customPref = (Preference) findPreference("customPref");
        customPref
                .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

                    public boolean onPreferenceClick(Preference preference) {
                        startActivity(new Intent(Settings.this, About.class));
                        Toast.makeText(getBaseContext(),
                                "The custom preference has been clicked",
                                Toast.LENGTH_LONG).show();
                        SharedPreferences customSharedPreference = getSharedPreferences(
                                "myCustomSharedPrefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = customSharedPreference
                                .edit();
                        editor.putString("myCustomPref",
                                "The preference has been clicked");
                        editor.commit();
                        return true;
                    }

                });
        Preference customFAQ = (Preference)findPreference("customFAQ");
        customFAQ.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Settings.this, FAQ.class));
                return true;
            }
        });
    }
}


