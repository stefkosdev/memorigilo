package com.stefkos.memorigilo.timechoose;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.stefkos.memorigilo.MainActivity;
import com.stefkos.memorigilo.R;

public class ChooseFragment2_2 extends Fragment {

    View v = null;
    ListView listMealsView;
    String[] listMeals;
    CheckBox toBreakfastCB = null;
    CheckBox toSecondBreakfastCB = null;
    CheckBox toLunchCB = null;
    CheckBox toPreDinnerCB = null;
    CheckBox toDinnerCB = null;
    Button accept = null;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        v = inflater.inflate(R.layout.ekran06_ustawalarm_doposilku, parent, false);

        toBreakfastCB = v.findViewById(R.id.toBreakfastCB);
        toSecondBreakfastCB = v.findViewById(R.id.toSecondBreakfastCB);
        toLunchCB = v.findViewById(R.id.toLunchCB);
        toPreDinnerCB = v.findViewById(R.id.toPreDinnerCB);
        toDinnerCB = v.findViewById(R.id.toDinnerCB);

        accept = v.findViewById(R.id.AcceptB);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( toBreakfastCB.isChecked() ) {
                    ChooseFragment2_2.setReminder(v, 0);
                }
                if( toSecondBreakfastCB.isChecked() ) {
                    ChooseFragment2_2.setReminder(v, 1);
                }
                if( toLunchCB.isChecked() ) {
                    ChooseFragment2_2.setReminder(v, 2);
                }
                if( toPreDinnerCB.isChecked() ) {
                    ChooseFragment2_2.setReminder(v, 3);
                }
                if( toDinnerCB.isChecked() ) {
                    ChooseFragment2_2.setReminder(v, 4);
                }
            }});
        /*
        // handle back button
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    return true;
                }
                return false;
            }
        } );

        listMealsView = v.findViewById(R.id.listOfMeals);

        listMeals = getResources().getStringArray(R.array.meals);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>( getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listMeals);
        listMealsView.setAdapter(adapter);


        listMealsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        });
*/
        return v;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    public static void setReminder( View view, int position )
    {
        int fromHour = MainActivity.foodTimes[position].getFromHour();
        int fromMins = MainActivity.foodTimes[position].getFromMin();
        int toHour = MainActivity.foodTimes[position].getToHour();
        int toMins = MainActivity.foodTimes[position].getToMin();

        String title = "";

        String dtype = ChooseActivity.getAlarmType();

        if( ChooseFragment42.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.alarm )
        {
            title = "Alarm";
        }
        else if( ChooseFragment42.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.posilek )
        {
            title = "Posilek";
        }
        else if( ChooseFragment42.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.spotkanie )
        {
            title = "spotkanie";
        }
        else if( ChooseFragment42.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.telewizja )
        {
            title = "telewizja";
        }
        else if( ChooseFragment42.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.zegar )
        {
            title = "zegar";
        }

        try {
            if (MainActivity.foodTimes[position].getEventID() > 0) {
                MainActivity.calendarManager.deleteEvent(MainActivity.foodTimes[position].getEventID());
            }
        }
        catch( SecurityException se )
        {
            Log.e(this.getClass().toString(), "Security Exception: " + se.toString() );
        }
        long ret = MainActivity.calendarManager.createEvent( title, "WEZ!", fromHour, fromMins, toHour, toMins );
        MainActivity.foodTimes[position].setEventID( ret );
        MainActivity.saveSettings(v.getContext() );

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);}
    }
}
