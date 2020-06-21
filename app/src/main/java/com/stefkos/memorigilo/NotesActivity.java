package com.stefkos.memorigilo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.stefkos.memorigilo.util.EventArrayAdapter;
import com.stefkos.memorigilo.util.FoodTimeEntry;

import java.util.Vector;

public class NotesActivity extends Activity {

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

        setContentView(R.layout.ekran08_zapiski_lista);

        //MainActivity.saveSettings(v.getContext() );
    }
}