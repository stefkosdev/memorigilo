package com.stefkos.memorigilo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.stefkos.memorigilo.util.EventArrayAdapter;
import com.stefkos.memorigilo.util.FoodTimeEntry;

import java.util.Vector;

public class EventListActivity extends Activity {

    ListView eventsListV = null;
    String events[] = new String[0];
    private static Vector<FoodTimeEntry> fte = null;

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

        setContentView(R.layout.activity_event_list);

        // get all calendar entries
        fte = MainActivity.calendarManager.readEntryTimes();

        EventArrayAdapter eaa = new EventArrayAdapter( this, fte );
        //final ArrayAdapter<FoodTimeEntry> adapter = new ArrayAdapter<FoodTimeEntry>( this,
        //        R.layout.event_row_layout, R.id.label, fte);

        eventsListV = (ListView) findViewById(R.id.listOfEvents);
        //eventsListV.setAdapter(adapter);
        eventsListV.setAdapter( eaa );

        //MainActivity.saveSettings(v.getContext() );
    }
}
