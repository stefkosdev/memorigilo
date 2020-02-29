package com.stefkos.memorigilo;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarManager {

    public static final String CALENDAR_NAME = "Memorigilo Calendar";
    public static final String ACCOUNT_NAME =  "Memorigilo";
    public static final String LOCATION = "Europe/Warsaw";
    public static final String OWNER_ACCOUNT = "memorigilo@gmail.com";
    public static final String ACCOUNT_NAME_PACKAGE = "com.memorigilo";

    private static long calendarID = -1;
    private static final String CLASS_NAME = "CalendarManager";

    private static Context lc = null;

    //
    //
    //

    public void init( Context c )
    {
        lc = c;
        if ( ( calendarID = getCalendarId()  ) == - 1) {
            Log.d("CalendarManager", "Calendar does not exist");
            createCalendar();
        }
    }

    //
    //
    //

    public void createCalendar( ) {
        Log.d( CLASS_NAME, "Create calendar");
        ContentValues values = new ContentValues();
        values.put(Calendars.ACCOUNT_NAME, ACCOUNT_NAME);
        values.put(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(Calendars.NAME, CALENDAR_NAME);
        values.put(Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_NAME);
        values.put(Calendars.CALENDAR_COLOR, 0xE6A627);
        values.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
        values.put(Calendars.OWNER_ACCOUNT, OWNER_ACCOUNT);
        values.put(Calendars.CALENDAR_TIME_ZONE, LOCATION);

        Uri.Builder builder = Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(Calendars.ACCOUNT_NAME, ACCOUNT_NAME_PACKAGE);
        builder.appendQueryParameter(Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true");
        Uri uri = lc.getContentResolver().insert(builder.build(), values);

        Log.d( CLASS_NAME, "Calendar created " + uri.toString() );
    }

    //
    //
    //

    public long getCalendarId( ) {
        try {
            Log.d( CLASS_NAME, "Get Calendar ID");
            String[] projection = new String[]{Calendars._ID};
            //String selection = Calendars.ACCOUNT_NAME + "="+ACCOUNT_NAME+" AND" + Calendars.ACCOUNT_TYPE + "=" + CalendarContract.ACCOUNT_TYPE_LOCAL;

            String selection = "(" + Calendars.ACCOUNT_NAME + " = ?) AND (" + Calendars.ACCOUNT_TYPE + " = ?)";
            String[] selectionArgs = new String[]{ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL};

            // use the same values as above:
            //String[] selArgs = new String[]{ACCOUNT_NAME, CalendarContract.ACCOUNT_TYPE_LOCAL};
            Cursor cursor = lc.getContentResolver().query(Calendars.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    null);
            if (cursor.moveToFirst()) {
                Log.d( CLASS_NAME, "Get Calendar ID, move to first");
                return cursor.getLong(0);
            }
        }catch( SecurityException se )
        {
            Log.d( CLASS_NAME, "Get Calendar ID, Security Exception " + se.toString() );
            return -2;
        }
        return -1;
    }

    //
    //
    //

    public long createEvent(String title, String description, int fromHour, int fromMins, int toHour, int toMins ) {
        long eventId = 0;

        Calendar fromDate = Calendar.getInstance();
        fromDate.set( fromDate.get(Calendar.YEAR), fromDate.get(Calendar.MONTH), fromDate.get(Calendar.DAY_OF_MONTH), fromHour, fromMins );

        Calendar toDate = Calendar.getInstance();
        toDate.set( toDate.get(Calendar.YEAR), toDate.get(Calendar.MONTH), toDate.get(Calendar.DAY_OF_MONTH), toHour, toMins );

        Log.d( CLASS_NAME, "Create event, time: " + fromDate.toString() );

        long startTime = fromDate.getTimeInMillis();
        long endTime = toDate.getTimeInMillis();
        TimeZone timeZone = TimeZone.getDefault();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, getCalendarId());
        values.put(CalendarContract.Events.ORGANIZER, ACCOUNT_NAME);
        values.put(CalendarContract.Events.TITLE, title);
        //values.put(CalendarContract.Events.EVENT_LOCATION, "Home");
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.EVENT_COLOR, 0xE6A627);
        values.put(CalendarContract.Events.DTSTART, startTime);
        values.put(CalendarContract.Events.DTEND, endTime );// startTime+ (1000 * 60 * 15)); // 15 minutes
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID() );// LOCATION);
        //values.put(CalendarContract.Events.EVENT_END_TIMEZONE, LOCATION);
        //values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=1;");
        values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;");
        values.put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PUBLIC);
        //values.put(CalendarContract.Events.SELF_ATTENDEE_STATUS, CalendarContract.Events.STATUS_CONFIRMED);
        values.put(CalendarContract.Events.ALL_DAY, 0);
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        //values.put(CalendarContract.Events.ORGANIZER, ACCOUNT_NAME);
        //values.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, 0);
        //values.put(CalendarContract.Events.GUESTS_CAN_MODIFY, 0);
        //values.put(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, 0);
        //values.put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        try
        {
            Uri uri = lc.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
            eventId = new Long(uri.getLastPathSegment());

            Log.d( CLASS_NAME, "Create event, eventid " + eventId );

            ContentValues reminderValues = new ContentValues();

            reminderValues.put(CalendarContract.Reminders.EVENT_ID, eventId );
            reminderValues.put(CalendarContract.Reminders.MINUTES, 1);
            reminderValues.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);

            String reminderUriString = "content://com.android.calendar/reminders";
            Uri reminderUri = lc.getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
        }catch( SecurityException se )
        {
            Log.d( CLASS_NAME, "Create event, Security Exception " + se.toString() );
        }
        return eventId;
    }

    //
    // Delete event
    //

    private int deleteEvent(long entryID) throws SecurityException{
        int iNumRowsDeleted = 0;
        /*
        if (ActivityCompat.checkSelfPermission(lc, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity, new String[]{Manifest.permission.WRITE_CALENDAR}, 1);
        }
        */

        Uri uri = CalendarContract.Instances.CONTENT_URI;

        String mSelectionClause = CalendarContract.Instances.EVENT_ID+ " = ?";
        String[] mSelectionArgs = {"2"};

        int updCount = lc.getContentResolver().delete(uri,mSelectionClause,mSelectionArgs);

        if( entryID > 0 ) {
            Uri eventsUri = Uri.parse("content://com.android.calendar/events");
            Uri eventUri = ContentUris.withAppendedId(eventsUri, entryID);
            iNumRowsDeleted = lc.getContentResolver().delete(eventUri, null, null);
        }
        Log.i(CLASS_NAME, "Deleted " + iNumRowsDeleted + " calendar entry.");

        return iNumRowsDeleted;
    }
}
