package com.unbeatable.vidhyadaan;

import android.support.multidex.MultiDexApplication;

/**
 * Created by rutvik on 30-08-2016 at 01:07 PM.
 */

public class App extends MultiDexApplication
{

    private String uid;

    public String getUid()
    {
        return uid;
    }

    public void setUid(final String uid)
    {
        this.uid = uid;
    }
}
