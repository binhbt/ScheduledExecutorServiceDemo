package com.sigma.demo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

/**
 * Created by t430 on 7/2/2018.
 */

public class BatteryUtil {
    private static final String TAG = "BatteryUtil";

    /**
     * get current battery percent
     * @param context
     * @return
     */
    public static int getBatteryPercentage(Context context) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;
        Log.e(TAG, batteryPct * 100+"");
        return (int) (batteryPct * 100);
    }
}
