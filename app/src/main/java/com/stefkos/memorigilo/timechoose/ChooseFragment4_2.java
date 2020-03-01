package com.stefkos.memorigilo.timechoose;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;

import com.stefkos.memorigilo.MainActivity;
import com.stefkos.memorigilo.R;
import com.stefkos.memorigilo.SettingsActivity;

public class ChooseFragment4_2 extends Fragment {

    View v = null;
    ListView listMealsView;
    String[] listMeals;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        v = inflater.inflate(R.layout.choose_fragment4_2, parent, false);

        listMealsView = v.findViewById(R.id.listOfMeals);

        listMeals = getResources().getStringArray(R.array.meals);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>( getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listMeals);
        listMealsView.setAdapter(adapter);


        listMealsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int fromHour = MainActivity.foodTimes[position].getFromHour();
                int fromMins = MainActivity.foodTimes[position].getFromMin();
                int toHour = MainActivity.foodTimes[position].getToHour();
                int toMins = MainActivity.foodTimes[position].getToMin();

                String title = "";

                String dtype = ChooseActivity.getAlarmType();

                if( dtype == "medicine")
                {
                    /*
                    imageIDS[0] = R.drawable.tabletka_pol;
                    imageIDS[1] = R.drawable.tabletka_cala;
                    imageIDS[2] = R.drawable.tabletka_antybiotyk;
                     */
                    if( ChooseFragment2.imageIDS[ ChooseActivity.getSelectedMedicIcon() ] == R.drawable.tabletka_pol )
                    {
                        title = "Wez pol tabletki";
                    }
                    else if( ChooseFragment2.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.tabletka_cala )
                    {
                        title = "Wez cala tabletke";
                    }
                    else if( ChooseFragment2.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.tabletka_antybiotyk )
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
                    if( ChooseFragment2.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.alarm )
                    {
                        title = "Alarm";
                    }
                    else if( ChooseFragment2.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.posilek )
                    {
                        title = "Posilek";
                    }
                    else if( ChooseFragment2.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.spotkanie )
                    {
                        title = "spotkanie";
                    }
                    else if( ChooseFragment2.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.telewizja )
                    {
                        title = "telewizja";
                    }
                    else if( ChooseFragment2.imageIDS[ ChooseActivity.getSelectedMedicIcon()] == R.drawable.zegar )
                    {
                        title = "zegar";
                    }
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
        });

        return v;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
