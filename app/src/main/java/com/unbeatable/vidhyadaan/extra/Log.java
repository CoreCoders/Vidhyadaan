package com.unbeatable.vidhyadaan.extra;

/**
 * Created by Rakshit on 05-08-2016 at 18:59.
 */
public class Log {
    static boolean showLog = true;

    public static void i(final String TAG, final String msg) {
        if (showLog) {
            android.util.Log.i(TAG, msg);
        }
    }
}
