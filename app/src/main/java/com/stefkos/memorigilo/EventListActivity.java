package com.stefkos.memorigilo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventListActivity extends AppCompatActivity {

    ListView eventsListV = null;
    String events[] = new String[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>( this,
                R.layout.event_row_layout, R.id.label, events);

        eventsListV = (ListView) findViewById(R.id.listOfEvents);
        eventsListV.setAdapter(adapter);
    }
}
