package com.unbeatable.vidhyadaan;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.unbeatable.vidhyadaan.extra.AppUtil;

/**
 * Created by rutvik on 30-08-2016 at 01:07 PM.
 */

public class App extends MultiDexApplication
{

    public static final String TAG = AppUtil.APP_TAG + App.class.getSimpleName();

    private String uid;

    public String getUid()
    {
        Log.i(TAG, "getUid: "+uid);
        return uid;
    }

    public void setUid(final String uid)
    {
        Log.i(TAG, "setUid: "+uid);
        this.uid = uid;
    }
}
