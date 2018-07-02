package com.sigma.demo;

import android.util.Log;

/**
 * Created by t430 on 7/2/2018.
 */

public class GpsUtil {
    private static final String TAG = "GpsUtil";

    /**
     * Use for Demo Only
     * In the real world You must use Location Manager
     * https://stackoverflow.com/questions/1513485/how-do-i-get-the-current-gps-location-programmatically-in-android
     * @return
     */
    public static final String getCurrentGps(){
        Log.e(TAG, "lat=1000,lon=1000");

        return "lat=1000,lon=1000";
    }
}
