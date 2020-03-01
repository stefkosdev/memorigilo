package com.stefkos.memorigilo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.stefkos.memorigilo.timechoose.ChooseActivity;
import com.stefkos.memorigilo.util.FoodTimeEntry;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends Activity {

    //
    // signed key password: 123456
    //

    Button leftButton = null;
    Button rightButton = null;
    Button upButton = null;
    Button okButton = null;
    Button settings = null;
    Button remind = null;

    private static final String PREFERENCES = "App_Preferences";

    public static FoodTimeEntry foodTimes[];
    // choose meal by ID and then change it
    public static int mealTimeChoose = 0;

    public static CalendarManager calendarManager = null;
    final int callbackId = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.foodTimes = new FoodTimeEntry[5];

        MainActivity.foodTimes[0] = new FoodTimeEntry();
        MainActivity.foodTimes[1] = new FoodTimeEntry();
        MainActivity.foodTimes[2] = new FoodTimeEntry();
        MainActivity.foodTimes[3] = new FoodTimeEntry();
        MainActivity.foodTimes[4] = new FoodTimeEntry();

        MainActivity.loadSettings( this.getApplication() );

        // set food times
        MainActivity.foodTimes[0].setName( getResources().getString(R.string.breakfast) );
        MainActivity.foodTimes[1].setName( getResources().getString(R.string.second_breakfast) );
        MainActivity.foodTimes[2].setName( getResources().getString(R.string.dinner) );
        MainActivity.foodTimes[3].setName( getResources().getString(R.string.pre_supper) );
        MainActivity.foodTimes[4].setName( getResources().getString(R.string.supper) );

        // check calendar permission
        checkPermissions(callbackId, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);

        // create Calendar Manager
        calendarManager = new CalendarManager();
        calendarManager.init( this );

        setContentView(R.layout.activity_main);

        // get all controls from layout
        //leftButton = findViewById(R.id.leftB );
        //rightButton = findViewById(R.id.rightB );
        //upButton = findViewById(R.id.upB );
        //okButton = findViewById(R.id.okB );
        settings = findViewById(R.id.settingsB);
        remind = findViewById(R.id.remindB);

        //
        //
        //

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //StatusApplication sapp = (StatusApplication)getApplication();

                //sapp.show_Notification( "Title", "Going to settings");

                Intent intent = new Intent(view.getContext(), SettingsActivity.class);
                view.getContext().startActivity(intent);}
        });

        //
        //
        //

        remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //StatusApplication sapp = (StatusApplication)getApplication();

                //sapp.show_Notification( "Title", "Going to remind section");

                Intent intent = new Intent(view.getContext(), ChooseActivity.class);
                view.getContext().startActivity(intent);}
        });
    }

    //
    // Check permissions
    //

    private void checkPermissions(int callbackId, String... permissionsId) {
        boolean permissions = true;
        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(this, permissionsId, callbackId);
    }

    @Override
    public void onRequestPermissionsResult(int callbackId, String permissions[], int[] grantResults)
    {

    }

    private static final String delimeter = "/";

    //
    // load settings
    //

    public static void loadSettings( Context ctx )
    {
        SharedPreferences settings;
        settings = ctx.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        //get the sharepref
        String prf = settings.getString("MEAL_TIMES", null);

        Log.d(MainActivity.class.toString(), "meal times load: " + prf );

        if( prf != null )
        {
            String s[] = prf.split( delimeter );
            if( s.length >= 5 ) {
                if (s[0] != null) MainActivity.foodTimes[0].fromString(s[0]);
                if (s[1] != null) MainActivity.foodTimes[1].fromString(s[1]);
                if (s[2] != null) MainActivity.foodTimes[2].fromString(s[2]);
                if (s[3] != null) MainActivity.foodTimes[3].fromString(s[3]);
                if (s[4] != null) MainActivity.foodTimes[4].fromString(s[4]);
            }
        }
    }

    public static void saveSettings( Context ctx )
    {
        SharedPreferences settings;
        settings = ctx.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = settings.edit();
        String save = MainActivity.foodTimes[0].toString() + delimeter + MainActivity.foodTimes[1].toString() + delimeter + MainActivity.foodTimes[2].toString() + delimeter + MainActivity.foodTimes[3].toString() + delimeter + MainActivity.foodTimes[4].toString();
        editor.putString("MEAL_TIMES", save );
        editor.commit();

        Log.d(MainActivity.class.toString(), "meal times save: " + save );
    }
}
