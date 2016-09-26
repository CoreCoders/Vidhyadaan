package com.unbeatable.vidhyadaan;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.unbeatable.vidhyadaan.extra.AppUtil;
import com.unbeatable.vidhyadaan.firebasemodle.User;

/**
 * Created by rutvik on 30-08-2016 at 01:07 PM.
 */

public class App extends MultiDexApplication
{

    public static final String TAG = AppUtil.APP_TAG + App.class.getSimpleName();

    private User user;

    public User getUser()
    {
        //Log.i(TAG, "getUser: "+uid);
        return user;
    }

    public void setUser(final User user)
    {
        //Log.i(TAG, "setUser: "+uid);
        this.user = user;
    }
}
