package com.stefkos.memorigilo.timechoose;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;

import com.stefkos.memorigilo.MainActivity;
import com.stefkos.memorigilo.R;

public class ChooseActivity extends FragmentActivity {//} AppCompatActivity {

    private static String alarmType = null;
    private static String timeType = null;
    private static int selectedMedicIcon = 0;
    private static int hours = 0;
    private static int mins = 0;

    //
    //
    //

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

        setContentView(R.layout.activity_choose);

        // Begin the transaction
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();

        ft.add(R.id.mainFragment, new ChooseFragment1());
// Complete the changes added above
        ft.commit();
    }

    //
    //
    //


    public static String getAlarmType() {
        return alarmType;
    }

    public static void setAlarmType(String alarmType) {
        ChooseActivity.alarmType = alarmType;
    }

    public static String getTimeType() {
        return timeType;
    }

    public static void setTimeType(String timeType) {
        ChooseActivity.timeType = timeType;
    }

    public static int getHours() {
        return hours;
    }

    public static void setHours(int hours) {
        ChooseActivity.hours = hours;
    }

    public static int getMins() {
        return mins;
    }

    public static void setMins(int mins) {
        ChooseActivity.mins = mins;
    }

    public static int getSelectedMedicIcon() {
        return selectedMedicIcon;
    }

    public static void setSelectedMedicIcon(int selectedMedicIcon) {
        ChooseActivity.selectedMedicIcon = selectedMedicIcon;
    }
}
