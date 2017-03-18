package com.exampl.acer.timernek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

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

        SharedPreferences sharedPreferences = getSharedPreferences("setting", Context.MODE_PRIVATE);
        Boolean musicRelsx = sharedPreferences.getBoolean("MusicForest", false);

        Preference customMusic = (Preference)findPreference("listRelaxMusic");
        if(!musicRelsx){
        customMusic.setEnabled(false);
        customMusic.setSummary(getText(R.string.settings_music));}
        Preference faq = (Preference)findPreference("customFAQ");
        faq.setEnabled(false);
    }
}


