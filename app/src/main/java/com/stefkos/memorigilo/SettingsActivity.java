package com.stefkos.memorigilo;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.preference.PreferenceFragmentCompat;

import com.stefkos.memorigilo.mealtimechoose.ChooseMealTimeActivity;

public class SettingsActivity extends Activity{// AppCompatActivity {

    Button setMealTimesB = null;
    Button manualB = null;
    Button setupSoundB = null;
    RadioButton lightThemeRB = null;
    RadioButton darkThemeRB = null;
    RadioGroup themeGroup = null;

    public static final int TONE_PICKER = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // we must set theme before its attached to view
        if( MainActivity.Theme.equals("Dark") ) {
            setTheme(R.style.DarkTheme);
        }
        else
        {
            setTheme(R.style.LightTheme);
        }

        setContentView(R.layout.ekran02_ustawienia);

        // attach buttons to objects
        setMealTimesB = findViewById(R.id.setMealsB );

        lightThemeRB = findViewById(R.id.LightRB);
        darkThemeRB = findViewById(R.id.DarkRB);
        themeGroup = findViewById(R.id.themeGroup);
        setupSoundB = findViewById(R.id.setupSoundB);

        if( MainActivity.Theme.equals("Dark") ) {
            themeGroup.check(themeGroup.getChildAt(1).getId());
        }
        else
        {
            themeGroup.check(themeGroup.getChildAt(0).getId());
        }

        // set button actions
        setMealTimesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ChooseMealTimeActivity.class);
                view.getContext().startActivity(intent);}
        });

        setupSoundB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Uri currentTone= RingtoneManager.getActualDefaultRingtoneUri(view.getContext(), RingtoneManager.TYPE_ALARM);
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
                startActivityForResult( intent, TONE_PICKER );
            }
        });

        lightThemeRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //darkThemeRB.setEnabled(false);
                MainActivity.Theme = "Light";
                setTheme(R.style.LightTheme);
                MainActivity.saveSettingsTheme(view.getContext());
            }
        });

        darkThemeRB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lightThemeRB.setEnabled(false);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TONE_PICKER) {
            if(resultCode == Activity.RESULT_OK){
                //String result=data.getStringExtra("result");
                MainActivity.RingToneUri = data.getStringExtra( RingtoneManager.EXTRA_RINGTONE_PICKED_URI );
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}