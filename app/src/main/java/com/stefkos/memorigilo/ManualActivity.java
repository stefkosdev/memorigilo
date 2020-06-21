package com.stefkos.memorigilo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.preference.PreferenceFragmentCompat;

import com.stefkos.memorigilo.mealtimechoose.ChooseMealTimeActivity;

public class ManualActivity extends Activity{// AppCompatActivity {

    Button setMealTimesB = null;
    Button manualB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( MainActivity.Theme.equals("Dark") ) {
            setTheme(R.style.DarkTheme);
        }
        else
        {
            setTheme(R.style.LightTheme);
        }

        setContentView(R.layout.ekran03_instrukcja);

        //setMealTimesB = findViewById(R.id.setMealsB );
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            //setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}