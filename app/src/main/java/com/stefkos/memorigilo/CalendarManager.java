package com.stefkos.memorigilo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.stefkos.memorigilo.util.FoodTimeEntry;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Pattern;

import static android.provider.CalendarContract.*;

public class CalendarManager {

    public static final String CALENDAR_NAME = "Memorigilo Calendar";
    public static final String ACCOUNT_NAME =  "Memorigilo";
    public static final String LOCATION = "Europe/Warsaw";
    public static final String OWNER_ACCOUNT = "memorigilo@gmail.com";
    public static final String ACCOUNT_NAME_PACKAGE = "com.memorigilo";

    private static long calendarID = -1;
    private static final String CLASS_NAME = "CalendarManager";

    private static Context lc = null;
    static Cursor cursor;

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
        values.put(Calendars.ACCOUNT_TYPE, ACCOUNT_TYPE_LOCAL);
        values.put(Calendars.NAME, CALENDAR_NAME);
        values.put(Calendars.CALENDAR_DISPLAY_NAME, CALENDAR_NAME);
        values.put(Calendars.CALENDAR_COLOR, 0xE6A627);
        values.put(Calendars.CALENDAR_ACCESS_LEVEL, Calendars.CAL_ACCESS_OWNER);
        values.put(Calendars.OWNER_ACCOUNT, OWNER_ACCOUNT);
        values.put(Calendars.CALENDAR_TIME_ZONE, LOCATION);

        Uri.Builder builder = Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(Calendars.ACCOUNT_NAME, ACCOUNT_NAME_PACKAGE);
        builder.appendQueryParameter(Calendars.ACCOUNT_TYPE, ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(CALLER_IS_SYNCADAPTER, "true");
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
            String[] selectionArgs = new String[]{ACCOUNT_NAME, ACCOUNT_TYPE_LOCAL};

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
        values.put(Events.CALENDAR_ID, getCalendarId());
        values.put(Events.ORGANIZER, ACCOUNT_NAME);
        values.put(Events.TITLE, title);
        //values.put(CalendarContract.Events.EVENT_LOCATION, "Home");
        values.put(Events.DESCRIPTION, description);
        values.put(Events.EVENT_COLOR, 0xE6A627);
        values.put(Events.DTSTART, startTime);
        values.put(Events.DTEND, endTime );// startTime+ (1000 * 60 * 15)); // 15 minutes
        values.put(Events.EVENT_TIMEZONE, timeZone.getID() );// LOCATION);
        //values.put(CalendarContract.Events.EVENT_END_TIMEZONE, LOCATION);
        //values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;COUNT=1;");
        values.put(Events.RRULE, "FREQ=DAILY;");
        values.put(Events.ACCESS_LEVEL, Events.ACCESS_PUBLIC);
        //values.put(CalendarContract.Events.SELF_ATTENDEE_STATUS, CalendarContract.Events.STATUS_CONFIRMED);
        values.put(Events.ALL_DAY, 0);
        values.put(Events.HAS_ALARM, 1);
        //values.put(CalendarContract.Events.ORGANIZER, ACCOUNT_NAME);
        //values.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, 0);
        //values.put(CalendarContract.Events.GUESTS_CAN_MODIFY, 0);
        //values.put(CalendarContract.Events.GUESTS_CAN_SEE_GUESTS, 0);
        //values.put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        try
        {
            Uri uri = lc.getContentResolver().insert(Events.CONTENT_URI, values);
            eventId = new Long(uri.getLastPathSegment());

            Log.d( CLASS_NAME, "Create event, eventid " + eventId );

            ContentValues reminderValues = new ContentValues();

            reminderValues.put(Reminders.EVENT_ID, eventId );
            reminderValues.put(Reminders.MINUTES, 1);
            reminderValues.put(Reminders.METHOD, Reminders.METHOD_ALERT);

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

    public int deleteEvent(long entryID) throws SecurityException{
        int iNumRowsDeleted = 0;
        /*
        if (ActivityCompat.checkSelfPermission(lc, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity, new String[]{Manifest.permission.WRITE_CALENDAR}, 1);
        }
        */

        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, Long.parseLong(String.valueOf(entryID)));
        int rows = lc.getContentResolver().delete(deleteUri, null, null);
        Toast.makeText(lc, "Event deleted", Toast.LENGTH_LONG).show();
/*
        Uri uri = CalendarContract.Instances.CONTENT_URI;

        String mSelectionClause = CalendarContract.Instances.EVENT_ID+ " = ?";
        String[] mSelectionArgs = {"" + entryID };

        int updCount = lc.getContentResolver().delete(uri,mSelectionClause,mSelectionArgs);

        if( entryID > 0 ) {
            Uri eventsUri = Uri.parse("content://com.android.calendar/events");
            Uri eventUri = ContentUris.withAppendedId(eventsUri, entryID);
            iNumRowsDeleted = lc.getContentResolver().delete(eventUri, null, null);
        }
        Log.i(CLASS_NAME, "Deleted " + iNumRowsDeleted + " calendar entry.");
*/
        return iNumRowsDeleted;
    }

    //
    //
    //

    public Vector <FoodTimeEntry> readEntryTimes()
    {
        Vector<FoodTimeEntry> fdte = new Vector<FoodTimeEntry>();

        ContentResolver contentResolver = lc.getContentResolver();

        // For each calendar, display all the events from the previous week to the end of next week.

        {
            Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
            //Uri.Builder builder = Uri.parse("content://com.android.calendar/calendars").buildUpon();
            long now = new Date().getTime();

            ContentUris.appendId(builder, now  );// * 10000);
            ContentUris.appendId(builder, now + DateUtils.DAY_IN_MILLIS );//* 10000);

            // get only events which happened today

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat    displayFormatter = new SimpleDateFormat("MMMM dd, yyyy (EEEE)");
            String stime=displayFormatter.format(calendar.getTime());

            SimpleDateFormat startFormatter = new SimpleDateFormat("MM/dd/yy");
            String dateString = startFormatter.format(calendar.getTime());

            long after = calendar.getTimeInMillis();

            SimpleDateFormat formatterr = new SimpleDateFormat("hh:mm:ss MM/dd/yy");


            Calendar startOfDay = Calendar.getInstance();

            Date startDateCCC = null;
            try {
                startDateCCC = formatterr.parse("0:00:00 " + dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startOfDay.setTime(startDateCCC);

            Calendar endOfDay = Calendar.getInstance();

            Date dateCCC = null;
            try {
                dateCCC = formatterr.parse("23:59:59 " + dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            endOfDay.setTime(dateCCC);

            // get only events which happened today (END)

            Long sd = new Long( (startOfDay.getTimeInMillis()) );
            Long ed = new Long( (endOfDay.getTimeInMillis()) );
            String selectString = "Events.calendar_id=" + calendarID + " and (" + Instances.DTSTART + ">=" + sd.toString() + " and " + CalendarContract.Instances.DTEND + "<" + ed.toString() + ")";

            Cursor eventCursor = contentResolver.query(builder.build(),
                    new String[]  { Events.TITLE, Events.DTSTART, Events.DTEND, Events._ID}, selectString ,
                    null, "startDay ASC, startMinute ASC");
                    //new String[]  { CalendarContract.Events.TITLE, "begin", "end", CalendarContract.Events.ALL_DAY,CalendarContract.Events._ID}, "Calendars._id=" + calendarID,
                    //null, "startDay ASC, startMinute ASC");

            Log.d( this.getClass().toString(), "eventCursor count="+eventCursor.getCount());
            if(eventCursor.getCount()>0)
            {
                if(eventCursor.moveToFirst())
                {
                    do
                    {
                        final String title = eventCursor.getString(0);
                        final Date begin = new Date(eventCursor.getLong(1));
                        final Date end = new Date(eventCursor.getLong(2));
                        final Long eventID = eventCursor.getLong(3);

                        Calendar beginCalendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                        beginCalendar.setTime(begin);   // assigns calendar to given date
                        Calendar endCalendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                        endCalendar.setTime(end);   // assigns calendar to given date

                        //  System.out.println("Title: " + title + " Begin: " + begin + " End: " + end +
                        //            " All Day: " + allDay);
                        //
                        Log.d( this.getClass().toString(), "Title:"+title);
                        Log.d( this.getClass().toString(), "Begin:"+begin);
                        Log.d( this.getClass().toString(), "End:"+end);
                        Log.d( this.getClass().toString(), "ID:"+eventID);

                        FoodTimeEntry newentry = new FoodTimeEntry();
                        newentry.setFromHour( beginCalendar.get(Calendar.HOUR) );
                        newentry.setFromMin( beginCalendar.get(Calendar.MINUTE) );
                        newentry.setToHour( endCalendar.get(Calendar.HOUR) );
                        newentry.setToMin( endCalendar.get(Calendar.MINUTE) );
                        newentry.setEventID( eventID );
                        newentry.setName( title );
                        // add to table

                        fdte.add( newentry );

                        /* the calendar control metting-begin events Respose  sub-string (starts....hare) */

                        Pattern p = Pattern.compile(" ");
                        String[] items = p.split(begin.toString());
                        String scalendar_metting_beginday,scalendar_metting_beginmonth,scalendar_metting_beginyear,scalendar_metting_begindate,scalendar_metting_begintime,scalendar_metting_begingmt;

                        scalendar_metting_beginday = items[0];
                        scalendar_metting_beginmonth = items[1];
                        scalendar_metting_begindate = items[2];
                        scalendar_metting_begintime = items[3];
                        scalendar_metting_begingmt = items[4];
                        scalendar_metting_beginyear = items[5];

                        String  calendar_metting_beginday = scalendar_metting_beginday;
                        String  calendar_metting_beginmonth = scalendar_metting_beginmonth.toString().trim();

                        int  calendar_metting_begindate = Integer.parseInt(scalendar_metting_begindate.trim());

                        String calendar_metting_begintime = scalendar_metting_begintime.toString().trim();
                        String calendar_metting_begingmt = scalendar_metting_begingmt;
                        int calendar_metting_beginyear = Integer.parseInt(scalendar_metting_beginyear.trim());


                        Log.d( this.getClass().toString(), "calendar_metting_beginday="+calendar_metting_beginday);

                        Log.d( this.getClass().toString(), "calendar_metting_beginmonth ="+calendar_metting_beginmonth);

                        Log.d( this.getClass().toString(), "calendar_metting_begindate ="+calendar_metting_begindate);

                        Log.d( this.getClass().toString(), "calendar_metting_begintime="+calendar_metting_begintime);

                        Log.d( this.getClass().toString(), "calendar_metting_begingmt ="+calendar_metting_begingmt);

                        Log.d( this.getClass().toString(), "calendar_metting_beginyear ="+calendar_metting_beginyear);

                        // the calendar control metting-begin events Respose  sub-string (starts....ends)

                        // the calendar control metting-end events Respose  sub-string (starts....hare)

                        Pattern p1 = Pattern.compile(" ");
                        String[] enditems = p.split(end.toString());
                        String scalendar_metting_endday,scalendar_metting_endmonth,scalendar_metting_endyear,scalendar_metting_enddate,scalendar_metting_endtime,scalendar_metting_endgmt;

                        scalendar_metting_endday = enditems[0];
                        scalendar_metting_endmonth = enditems[1];
                        scalendar_metting_enddate = enditems[2];
                        scalendar_metting_endtime = enditems[3];
                        scalendar_metting_endgmt = enditems[4];
                        scalendar_metting_endyear = enditems[5];


                        String  calendar_metting_endday = scalendar_metting_endday;
                        String  calendar_metting_endmonth = scalendar_metting_endmonth.toString().trim();

                        int  calendar_metting_enddate = Integer.parseInt(scalendar_metting_enddate.trim());

                        String calendar_metting_endtime = scalendar_metting_endtime.toString().trim();
                        String calendar_metting_endgmt = scalendar_metting_endgmt;
                        int calendar_metting_endyear = Integer.parseInt(scalendar_metting_endyear.trim());


                        Log.d( this.getClass().toString(), "calendar_metting_beginday="+calendar_metting_endday);

                        Log.d( this.getClass().toString(), "calendar_metting_beginmonth ="+calendar_metting_endmonth);

                        Log.d( this.getClass().toString(), "calendar_metting_begindate ="+calendar_metting_enddate);

                        Log.d( this.getClass().toString(), "calendar_metting_begintime="+calendar_metting_endtime);

                        Log.d( this.getClass().toString(), "calendar_metting_begingmt ="+calendar_metting_endgmt);

                        Log.d( this.getClass().toString(), "calendar_metting_beginyear ="+calendar_metting_endyear);

                        /* the calendar control metting-end events Respose  sub-string (starts....ends) */

                        Log.d( this.getClass().toString(), "only date begin of events="+begin.getDate());
                        Log.d( this.getClass().toString(), "only begin time of events="+begin.getHours() + ":" +begin.getMinutes() + ":" +begin.getSeconds());


                        Log.d( this.getClass().toString(), "only date begin of events="+end.getDate());
                        Log.d( this.getClass().toString(), "only begin time of events="+end.getHours() + ":" +end.getMinutes() + ":" +end.getSeconds());

                        //beg_date = begin.getDate();
                        //mbeg_date = begin.getDate()+"/"+calendar_metting_beginmonth+"/"+calendar_metting_beginyear;
                        //beg_time = begin.getHours();

                        //Log.d( this.getClass().toString(), "the vaule of mbeg_date="+mbeg_date.toString().trim());
                        //end_date = end.getDate();
                        //end_time = end.getHours();


                        //CallHandlerUI.metting_begin_date.add(beg_date.toString());
                        //CallHandlerUI.metting_begin_mdate.add(mbeg_date.toString());

                        //CallHandlerUI.metting_begin_mtime.add(calendar_metting_begintime.toString());

                        //CallHandlerUI.metting_end_date.add(end_date.toString());
                        //CallHandlerUI.metting_end_time.add(end_time.toString());
                        //CallHandlerUI.metting_end_mtime.add(calendar_metting_endtime.toString());

                    }
                    while(eventCursor.moveToNext());
                }
            }
        }

        return fdte;
    }
}
