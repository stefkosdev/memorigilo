package com.stefkos.memorigilo.mealtimechoose;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.stefkos.memorigilo.MainActivity;
import com.stefkos.memorigilo.R;
import com.stefkos.memorigilo.timechoose.ChooseActivity;
import com.stefkos.memorigilo.util.TimeUtil;

import java.text.DecimalFormat;

public class ChooseMealFragment2 extends Fragment {
    TimePicker fromTimePickerB = null;
    TimePicker toTimePickerB = null;
    TextView title = null;
    Button setTimeB = null;

    NumberPicker fromNumberPicker = null;
    NumberPicker toNumberPicker = null;

    //
    //
    //

    private static final int INTERVAL = 5;
    private static final DecimalFormat FORMATTER = new DecimalFormat("00");

    //
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View v = inflater.inflate(R.layout.mealtime_choose_fragment2, parent, false);

        setTimeB = (Button) v.findViewById(R.id.setMealTimeB);
        fromTimePickerB = (TimePicker) v.findViewById(R.id.fromTimePicker);
        toTimePickerB = (TimePicker) v.findViewById(R.id.toTimePicker);
        title = (TextView) v.findViewById(R.id.mealSelectTitle);

        fromTimePickerB.setIs24HourView( true );
        toTimePickerB.setIs24HourView( true );

        fromTimePickerB.setHour( MainActivity.foodTimes[ MainActivity.mealTimeChoose ].getFromHour() );
        fromTimePickerB.setMinute( MainActivity.foodTimes[ MainActivity.mealTimeChoose ].getFromMin()/INTERVAL );

        toTimePickerB.setHour( MainActivity.foodTimes[ MainActivity.mealTimeChoose ].getToHour() );
        toTimePickerB.setMinute( MainActivity.foodTimes[ MainActivity.mealTimeChoose ].getToMin()/INTERVAL );

        fromNumberPicker = setMinutePicker( fromTimePickerB );
        toNumberPicker = setMinutePicker( toTimePickerB );

        title.setText( MainActivity.foodTimes[ MainActivity.mealTimeChoose ].getName() );

        ChooseActivity.setAlarmType( null );

        setTimeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

            MainActivity.foodTimes[ MainActivity.mealTimeChoose ].setFromMin( getMinute( fromTimePickerB, fromNumberPicker ) );
            MainActivity.foodTimes[ MainActivity.mealTimeChoose ].setToMin( getMinute( toTimePickerB, toNumberPicker ) );
            MainActivity.foodTimes[ MainActivity.mealTimeChoose ].setFromHour( fromTimePickerB.getHour() );
            MainActivity.foodTimes[ MainActivity.mealTimeChoose ].setToHour( toTimePickerB.getHour() );

            boolean storeValue = TimeUtil.CheckTime( fromTimePickerB, toTimePickerB );

            if( storeValue ) {
                MainActivity.saveSettings(getActivity().getApplication());

                ChooseMealFragment1 cf = new ChooseMealFragment1();
                ft.replace(R.id.mainMealChooseFragment, cf);
                ft.addToBackStack(null);
                ft.commit();
            }
            else
            {
                Toast.makeText( getActivity().getApplication(), "Fix your time!", Toast.LENGTH_SHORT).show();
            }
        }});

        return v;
    }

    //
    //
    //

    public static NumberPicker setMinutePicker( TimePicker picker ) {
        int numValues = 60 / INTERVAL;
        NumberPicker minutePicker = null;
        String[] displayedValues = new String[numValues];
        for (int i = 0; i < numValues; i++) {
            displayedValues[i] = FORMATTER.format(i * INTERVAL);
        }


        View minute = picker.findViewById(Resources.getSystem().getIdentifier("minute", "id", "android"));
        if ((minute != null) && (minute instanceof NumberPicker)) {
            minutePicker = (NumberPicker) minute;
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(numValues - 1);
            minutePicker.setDisplayedValues(displayedValues);
        }
        return minutePicker;
    }

    public static int getMinute( TimePicker picker, NumberPicker minutePicker ) {
        if (minutePicker != null) {
            return (minutePicker.getValue() * INTERVAL);
        } else {
            return picker.getCurrentMinute();
        }
    }

    //
    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    //

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here

    }
}