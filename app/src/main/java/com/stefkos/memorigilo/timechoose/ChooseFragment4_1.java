package com.stefkos.memorigilo.timechoose;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.stefkos.memorigilo.MainActivity;
import com.stefkos.memorigilo.R;

public class ChooseFragment4_1 extends Fragment {

    Button setTimeB = null;
    TimePicker tpicker = null;
    View v = null;
    int selectedIcon = 0;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        v = inflater.inflate(R.layout.choose_fragment4_1, parent, false);

        setTimeB = v.findViewById(R.id.setTimeB);
        tpicker = v.findViewById(R.id.alarmTimePicker);

        tpicker.setIs24HourView( true );

        setTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour = tpicker.getHour();
                int mins = tpicker.getMinute();

                String title = "";

                String dtype = ChooseActivity.getAlarmType();

                if( dtype == "medicine")
                {
                    /*
                    imageIDS[0] = R.drawable.tabletka_pol;
                    imageIDS[1] = R.drawable.tabletka_cala;
                    imageIDS[2] = R.drawable.tabletka_antybiotyk;
                     */
                    if( ChooseFragment2.imageIDS[ selectedIcon] == R.drawable.tabletka_pol )
                    {
                        title = "Wez pol tabletki";
                    }
                    else if( ChooseFragment2.imageIDS[ selectedIcon] == R.drawable.tabletka_cala )
                    {
                        title = "Wez cala tabletke";
                    }
                    else if( ChooseFragment2.imageIDS[ selectedIcon] == R.drawable.tabletka_antybiotyk )
                    {
                        title = "Wez antybiotyk";
                    }
                }
                else
                {
                    /*
                    imageIDS[0] = R.drawable.alarm;
                    imageIDS[1] = R.drawable.posilek;
                    imageIDS[2] = R.drawable.spotkanie;
                    imageIDS[3] = R.drawable.telewizja;
                    imageIDS[4] = R.drawable.zegar;
                     */
                    if( ChooseFragment2.imageIDS[ selectedIcon] == R.drawable.alarm )
                    {
                        title = "Alarm";
                    }
                    else if( ChooseFragment2.imageIDS[ selectedIcon] == R.drawable.posilek )
                    {
                        title = "Posilek";
                    }
                    else if( ChooseFragment2.imageIDS[ selectedIcon] == R.drawable.spotkanie )
                    {
                        title = "spotkanie";
                    }
                    else if( ChooseFragment2.imageIDS[ selectedIcon] == R.drawable.telewizja )
                    {
                        title = "telewizja";
                    }
                    else if( ChooseFragment2.imageIDS[ selectedIcon] == R.drawable.zegar )
                    {
                        title = "zegar";
                    }
                }

                int toHour = 0;
                int toMins = 0;
                if( (mins+15) > 59 )    // 15mins reminder
                {
                    toHour = hour+1;
                    toMins = (mins+15)-60;
                }
                else
                {
                    toHour = hour;
                    toMins = mins;
                }

                MainActivity.calendarManager.createEvent( title, "WEZ!", hour, mins, toHour, toMins );

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);}
        });

        return v;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
