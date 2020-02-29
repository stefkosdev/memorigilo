package com.stefkos.memorigilo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/**
 * Custom Application which can detect application state of whether it enter
 * background or enter foreground.
 *
 * @author shuang
 * @reference http://www.vardhan-justlikethat.blogspot.sg/2014/02/android-solution-to-detect-when-android.html
 */
public class StatusApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static final int STATE_UNKNOWN = 0x00;
    public static final int STATE_CREATED = 0x01;
    public static final int STATE_STARTED = 0x02;
    public static final int STATE_RESUMED = 0x03;
    public static final int STATE_PAUSED = 0x04;
    public static final int STATE_STOPPED = 0x05;
    public static final int STATE_DESTROYED = 0x06;

    private static final int FLAG_STATE_FOREGROUND = -1;
    private static final int FLAG_STATE_BACKGROUND = -2;

    private int mCurrentState = STATE_UNKNOWN;
    private int mStateFlag = FLAG_STATE_BACKGROUND;

    public static int notification_id = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mCurrentState = STATE_UNKNOWN;
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // mCurrentState = STATE_CREATED;
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (mCurrentState == STATE_UNKNOWN || mCurrentState == STATE_STOPPED) {
            if (mStateFlag == FLAG_STATE_BACKGROUND) {
                //applicationWillEnterForeground();
                mStateFlag = FLAG_STATE_FOREGROUND;
            }
        }
        mCurrentState = STATE_STARTED;

    }

    @Override
    public void onActivityResumed(Activity activity) {
        mCurrentState = STATE_RESUMED;

    }

    @Override
    public void onActivityPaused(Activity activity) {
        mCurrentState = STATE_PAUSED;

    }

    @Override
    public void onActivityStopped(Activity activity) {
        mCurrentState = STATE_STOPPED;

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mCurrentState = STATE_DESTROYED;
        show_Notification("Title", "onActivityDestroyed");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        //show_Notification("Title", "onTrimMemory");

        if (mCurrentState == STATE_STOPPED && level >= TRIM_MEMORY_UI_HIDDEN) {
            if (mStateFlag == FLAG_STATE_FOREGROUND) {
                //applicationDidEnterBackground();
                mStateFlag = FLAG_STATE_BACKGROUND;
            }
        }else if (mCurrentState == STATE_DESTROYED && level >= TRIM_MEMORY_UI_HIDDEN) {
            if (mStateFlag == FLAG_STATE_FOREGROUND) {
                //applicationDidDestroyed();
                mStateFlag = FLAG_STATE_BACKGROUND;
            }
        }
    }

    /**
     * The method be called when the application been destroyed. But when the
     * device screen off,this method will not invoked.
     */
    //protected abstract void applicationDidDestroyed();

    /**
     * The method be called when the application enter background. But when the
     * device screen off,this method will not invoked.
     */
    //protected abstract void applicationDidEnterBackground();

    /**
     * The method be called when the application enter foreground.
     */
    //protected abstract void applicationWillEnterForeground();

    //
    //
    //

    public void presentNotification(int visibility, String title, String text) {
        Notification notification = new NotificationCompat.Builder(this)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSmallIcon( R.drawable.ic_launcher_background )
                .setVisibility(visibility).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notification_id++, notification);
    }



    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    public void show_Notification( String title, String text ){

        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        String CHANNEL_ID="MYCHANNEL";
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,"name",NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent= PendingIntent.getActivity(getApplicationContext(),1,intent,0);
        Notification notification=new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText( text )
                .setContentTitle( title )
                .setContentIntent(pendingIntent)
                .addAction(android.R.drawable.sym_action_chat,"Title",pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .build();

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(1,notification);


    }
}