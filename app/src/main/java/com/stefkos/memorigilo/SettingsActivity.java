package com.stefkos.memorigilo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.preference.PreferenceFragmentCompat;

import com.stefkos.memorigilo.mealtimechoose.ChooseMealTimeActivity;

public class SettingsActivity extends Activity{// AppCompatActivity {

    Button setMealTimesB = null;
    Button manualB = null;
    RadioButton lightThemeRB = null;
    RadioButton darkThemeRB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ekran02_ustawienia);

        setMealTimesB = findViewById(R.id.setMealsB );

        lightThemeRB = findViewById(R.id.LightRB);
        darkThemeRB = findViewById(R.id.DarkRB);

        setMealTimesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ChooseMealTimeActivity.class);
                view.getContext().startActivity(intent);}
        });

        lightThemeRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                darkThemeRB.setSelected(false);
                MainActivity.Theme = "Light";
                setTheme(R.style.LightTheme);
                MainActivity.saveSettingsTheme(view.getContext());
            }
        });

        darkThemeRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightThemeRB.setSelected(false);
                MainActivity.Theme = "Dark";
                setTheme(R.style.DarkTheme);
                MainActivity.saveSettingsTheme(view.getContext());
            }
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