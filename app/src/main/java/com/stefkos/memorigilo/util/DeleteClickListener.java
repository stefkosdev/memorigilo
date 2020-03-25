package com.stefkos.memorigilo.util;

import android.view.View;

import com.stefkos.memorigilo.MainActivity;

public class DeleteClickListener  implements View.OnClickListener {

    long eventID = 0;
    int listId = 0;
    EventArrayAdapter eventAA = null;

    public DeleteClickListener( long eid, int lid, EventArrayAdapter eaa ) {
        this.eventID = eid;
        this.listId = lid;
        this.eventAA = eaa;
    }

    @Override
    public void onClick(View v)
    {
        //read your lovely variable
        System.out.println( " on click " + eventID );

        MainActivity.calendarManager.deleteEvent( this.eventID );
        this.eventAA.remove(eventAA.getItem(this.listId));
        this.eventAA.notifyDataSetChanged();

        MainActivity.saveSettings(v.getContext() );
    }
}
