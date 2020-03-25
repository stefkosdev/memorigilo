package com.stefkos.memorigilo.util;

//
// class which handle food times set by user
//

import android.util.Log;

public class FoodTimeEntry {
    private int fromMin = 0;
    private int fromHour = 0;
    private int toMin = 0;
    private int toHour = 0;
    private long eventID = 0;

    public FoodTimeEntry()
    {
        fromMin = fromHour = toMin = toHour = 0;
    }

    private String name = null;

    public int getFromMin() {
        return fromMin;
    }

    public void setFromMin(int fromMin) {
        this.fromMin = fromMin;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getToMin() {
        return toMin;
    }

    public void setToMin(int toMin) {
        this.toMin = toMin;
    }

    public int getToHour() {
        return toHour;
    }

    public void setToHour(int toHour) {
        this.toHour = toHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getEventID() {
        return eventID;
    }

    public void setEventID(long eventID) {
        this.eventID = eventID;
    }

    //
    // convert String to int
    //

    private int toInt( String text )
    {
        int ret = 0;
        try{
            ret = (new Integer( text )).intValue();
        }catch( Exception e )
        {
            ret = -1;
        }
        return ret;
    }

    //
    // convert String to long
    //

    private long toLong( String text )
    {
        long ret = 0;
        try{
            ret = (new Long( text )).intValue();
        }catch( Exception e )
        {
            ret = -1;
        }
        return ret;
    }

    public int fromString( String text )
    {
        String s[] = text.split("_");
        Log.d( this.getClass().toString(), "Load from: "  + s.toString() + " len " + s.length );
        if( s.length >= 5 )
        {
            Log.d( this.getClass().toString(), "0: "  + s[0] );
            name = s[0];

            fromHour = toInt( s[1] );
            Log.d( this.getClass().toString(), "1: "  + s[1] );
            fromMin = toInt( s[2] );
            Log.d( this.getClass().toString(), "2: "  + s[2] );
            toHour = toInt( s[3] );
            Log.d( this.getClass().toString(), "3: "  + s[3] );
            toMin = toInt( s[4] );
            Log.d( this.getClass().toString(), "4: "  + s[4] );

            try {
                eventID = toLong(s[5]);
                Log.d( this.getClass().toString(), "5: "  + s[5] );
            }catch( ArrayIndexOutOfBoundsException aiob )   //if old config is loaded we must know about that
            {
                eventID = -1;
                return 2;
            }
        }
        else
        {
            return 1;
        }

        return 0;
    }

    public String toString()
    {
        return "" + name + "_" + fromHour + "_" + fromMin + "_" + toHour + "_" + toMin + "_" + eventID;
    }
}
