package com.stefkos.memorigilo.util;

import android.widget.TimePicker;

/*
    @description Validate 2 TimePicker fields

    @param fromTimePickerB
    @param toTimePickerB
 */

public class TimeUtil {
    public static boolean CheckTime(TimePicker fromTimePickerB, TimePicker toTimePickerB )
    {
        boolean storeValue = false;
        if( fromTimePickerB.getHour() > toTimePickerB.getHour()  )
        {

        }
        else
        {
            if( fromTimePickerB.getMinute() >= toTimePickerB.getMinute() )
            {

            }
            else
            {
                storeValue = true;
            }
        }
        return storeValue;
    }
}
