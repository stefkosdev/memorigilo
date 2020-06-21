package com.stefkos.memorigilo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.preference.PreferenceFragmentCompat;

import com.stefkos.memorigilo.mealtimechoose.ChooseMealTimeActivity;

public class SettingsActivity extends Activity{// AppCompatActivity {

    Button setMealTimesB = null;
    Button manualB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ekran02_ustawienia);

        setMealTimesB = findViewById(R.id.setMealsB );

        setMealTimesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ChooseMealTimeActivity.class);
                view.getContext().startActivity(intent);}
        });
        /*
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settingsB, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        */
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            //setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}